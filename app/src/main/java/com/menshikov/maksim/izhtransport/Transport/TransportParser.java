package com.menshikov.maksim.izhtransport.Transport;

import android.location.Location;

import com.menshikov.maksim.izhtransport.Sources.ITransportInfoSource;

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
    public ArrayList<Location> getTransportPositions(int idTransport, int number) throws InterruptedException
    {
        ArrayList<Location> locations = new ArrayList<Location>();

        source.setTransportParameters(idTransport, number);
        String response = source.getServerResponse();
        if (response == null)
        {
            return null;
        }

        ArrayList<String> placemarks = this.getStringByPattern(response,"(?<=myPlacemark = )[\\s\\S]+?(?=doc_layers)");
        if (placemarks.isEmpty())
            return null;

        for (String placemark : placemarks)
        {
            ArrayList<String> locationsString = this.getStringByPattern(placemark,"[0-9]{2}\\.[0-9]{1,4}, [0-9]{2}\\.[0-9]{1,4}");
            if (!locationsString.isEmpty())
            {
                Location location = new Location("izh");
                int del = locationsString.get(0).indexOf(',');

                location.setLongitude(Double.parseDouble(locationsString.get(0).substring(0, del)));
                String temp = locationsString.get(0).substring(del + 1, locationsString.get(0).length());
                location.setLatitude(Double.parseDouble(temp));
                locations.add(location);
            }

        }

        return locations;
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
