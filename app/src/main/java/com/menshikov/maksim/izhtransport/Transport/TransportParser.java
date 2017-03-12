package com.menshikov.maksim.izhtransport.Transport;

import android.location.Location;

import com.menshikov.maksim.izhtransport.Sources.ITransportInfoSource;
import com.menshikov.maksim.izhtransport.map.CoordHelper;
import com.menshikov.maksim.izhtransport.map.MapPoint;
import com.menshikov.maksim.izhtransport.map.MoveableMapPoint;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
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

    public ArrayList<MapPoint> getTransportPoints() throws IOException
    {
        ArrayList<MapPoint> transportPoints = new ArrayList<MapPoint>();

        ArrayList<String> placemarks = this.ParsePlacemarks();

        for (String placemark : placemarks)
        {
            MoveableMapPoint transportPoint = this.BuildTransportPoint(placemark);
            transportPoints.add(transportPoint);
        }

        return transportPoints;
    }

    private MoveableMapPoint BuildTransportPoint(String placemark) throws IOException
    {
        int transportType = this.ParseTransportType(placemark);

        MoveableMapPoint transportPoint = TransportFactory.createTransportPoint(transportType);

        int id = this.ParseTransportId(placemark);
        transportPoint.setId(id);

        Location location = this.ParseTransportLocation(placemark);

        transportPoint.setGeoLocation(location);
        CoordHelper.addMapPointCoords(transportPoint);

       // transportPoint.setNumber(this.ParseTransportNumber(placemark));

        float direction = this.ParseDegreeDirection(placemark);
        transportPoint.setDegreeDirection(direction);
        return transportPoint;
    }

    private float ParseDegreeDirection(String placemark) throws IOException
    {
        ArrayList<String> directionPngStrings = this.getStringByPattern(placemark, "\\d*\\.png");
        if (directionPngStrings.isEmpty())
            throw new IOException();

        String directionPngString = directionPngStrings.get(0);

        ArrayList<String> directionStrings = this.getStringByPattern(directionPngString, "\\d{1,3}");
        if (directionStrings.isEmpty())
            return (float) -1;

        String directionString = directionStrings.get(0);

        return Float.parseFloat(directionString);
    }

    private Location ParseTransportLocation(String placemark) throws IOException
    {
        ArrayList<String> locationsString = this.getStringByPattern(placemark, "[0-9]{2}\\.[0-9]{1,4}, [0-9]{2}\\.[0-9]{1,4}");
        if (locationsString.isEmpty())
            throw new IOException();

        Location location = new Location("izh");
        int delimiterIndex = locationsString.get(0).indexOf(',');

        location.setLongitude(Double.parseDouble(locationsString.get(0).substring(0, delimiterIndex)));
        String temp = locationsString.get(0).substring(delimiterIndex + 1, locationsString.get(0).length());
        location.setLatitude(Double.parseDouble(temp));

        return location;
    }

    private int ParseTransportId(String placemark) throws IOException
    {
        ArrayList<String> idString = this.getStringByPattern(placemark, "id:\\s?'\\d*'");
        if (idString.isEmpty())
            throw new IOException();
        ArrayList<String> id = this.getStringByPattern(idString.get(0), "[0-9]{1,}");
        if (id.isEmpty())
            throw new IOException();
        return Integer.parseInt(id.get(0));
    }

    private int ParseTransportNumber(String placemark) throws IOException
    {
        ArrayList<String> numberString = this.getStringByPattern(placemark, "№\\d{1,}");
        if (numberString.isEmpty())
            throw new IOException();
        ArrayList<String> number = this.getStringByPattern(numberString.get(0), "[0-9]{1,}");
        if (number.isEmpty())
            throw new IOException();
        return Integer.parseInt(number.get(0));
    }


    private int ParseTransportType(String placemark) throws IOException
    {
        ArrayList<String> typeString = this.getStringByPattern(placemark, "mode:\\s?'\\d'");
        if (typeString.isEmpty())
            throw new IOException();

        ArrayList<String> type = this.getStringByPattern(typeString.get(0), "\\d");
        if (type.isEmpty())
            throw new IOException();

        return Integer.parseInt(type.get(0));
    }


    private ArrayList<String> ParsePlacemarks() throws IOException
    {
        String response = this.GetServerResponse();
        if (response == null)
            throw new IOException();

        ArrayList<String> placemarks = this.getStringByPattern(response, "(?<=myPlacemark = )[\\s\\S]+?(?=doc_layers)");
        if (placemarks.isEmpty())
            throw new IOException();

        return placemarks;
    }

    private String GetServerResponse()
    {
        String response = null;
        try
        {
            response = this.source.getServerResponse();
        } catch (InterruptedException e)
        {
        }
        return response;
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
