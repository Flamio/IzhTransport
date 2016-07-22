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
    public ArrayList<Location> getTransportPositions(int idTransport, int number) throws InterruptedException {
        ArrayList<Location> locations = new ArrayList<Location>();

        source.setTransportParameters(idTransport,number);
        String response = source.getServerResponse();
        if (response == null)
        {
            return null;
        }
        Pattern p = Pattern.compile("[0-9]{2}\\.[0-9]{1,4}, [0-9]{2}\\.[0-9]{1,4}");
        Matcher m = p.matcher(response);

        while (m.find())
        {
            String s = m.group(0);
            Location location = new Location("izh");
            int del = s.indexOf(',');

            location.setLongitude(Double.parseDouble(s.substring(0,del)));
            String temp = s.substring(del+1,s.length());
            location.setLatitude(Double.parseDouble(temp));
            locations.add(location);
        }

        return locations;
    }

}
