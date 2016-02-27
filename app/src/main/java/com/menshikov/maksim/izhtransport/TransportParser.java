package com.menshikov.maksim.izhtransport;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;
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

    public ArrayList<Location> getTransportPositions(int idTransport, int number)
    {
        ArrayList<Location> locations = new ArrayList<Location>();

        source.setTransportParameters(idTransport,number);
        String response = source.getServerResponse();

        Pattern p = Pattern.compile("[0-9]{2}\\.[0-9]{4}, [0-9]{2}\\.[0-9]{4}");
        Matcher m = p.matcher(response);

        while (m.find())
        {
            String s = m.group(1);
            Location location = new Location("izh");
            location.setLatitude(Double.parseDouble(s.substring(0,7)));
            location.setLongitude(Double.parseDouble(s.substring(9,7)));
            locations.add(location);
        }

        return locations;


    }

}
