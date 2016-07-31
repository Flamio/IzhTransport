package com.menshikov.maksim.izhtransport.Transport;

import android.location.Location;

import com.menshikov.maksim.izhtransport.Sources.ITransportInfoSource;
import com.menshikov.maksim.izhtransport.map.MapPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maksim on 27.02.2016.
 */


public class TransportParser
{
    private ITransportInfoSource source;


    public TransportParser(ITransportInfoSource source)
    {
        this.source = source;
    }

    // TODO сделать чтобы возвращал IMapPoint
    public ArrayList<MapPoint> getTransportPositions(int idTransport, int number) throws InterruptedException, IOException
    {
        ArrayList<MapPoint> transportPoints = new ArrayList<MapPoint>();

        source.setTransportParameters(idTransport, number);
        String response = source.getServerResponse();
        if (response == null)
            throw new IOException();

        ArrayList<String> placemarks = this.getStringByPattern(response,"(?<=myPlacemark = )[\\s\\S]+?(?=doc_layers)");
        if (placemarks.isEmpty())
            return null;

        for (String placemark : placemarks)
        {
            MapPoint transportPoint = null;

            ArrayList<String> typeString = this.getStringByPattern(placemark,"mode:\\s?'\\d'");
            if (!typeString.isEmpty())
            {
                ArrayList<String> type = this.getStringByPattern(typeString.get(0),"\\d");
                if (!type.isEmpty())
                {
                    transportPoint = TransportFactory.createTransportPoint(Integer.parseInt(type.get(0)));
                    if (transportPoint == null)
                        continue;
                }
            }

            ArrayList<String> locationsString = this.getStringByPattern(placemark,"[0-9]{2}\\.[0-9]{1,4}, [0-9]{2}\\.[0-9]{1,4}");
            if (!locationsString.isEmpty())
            {
                Location location = new Location("izh");
                int del = locationsString.get(0).indexOf(',');

                location.setLongitude(Double.parseDouble(locationsString.get(0).substring(0, del)));
                String temp = locationsString.get(0).substring(del + 1, locationsString.get(0).length());
                location.setLatitude(Double.parseDouble(temp));
                transportPoint.setGeoLocation(location);

                transportPoints.add(transportPoint);
            }
        }

        return transportPoints;
    }

    private ArrayList<String> getStringByPattern(String input, String pattern)
    {
        Pattern localPattern = Pattern.compile(pattern);
        Matcher matcher = localPattern.matcher(input);
        ArrayList<String> found = new ArrayList<>();
        while (matcher.find())
            found.add(matcher.group(0));
        return found;
    }
}
