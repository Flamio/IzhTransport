package com.menshikov.maksim.izhtransport.Transport;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.menshikov.maksim.izhtransport.R;
import com.menshikov.maksim.izhtransport.ResourceManager;
import com.menshikov.maksim.izhtransport.TransportMapPoint;

import java.io.IOException;

/**
 * Created by Maksim on 29.07.2016.
 */
public class TransportFactory
{
    public static Bitmap busIcon =  BitmapFactory.decodeResource(ResourceManager.Instance().getResources(), R.drawable.bus);
    public static Bitmap trolleyIcon =  BitmapFactory.decodeResource(ResourceManager.Instance().getResources(), R.drawable.trolleybus);
    public static Bitmap tramIcon =  BitmapFactory.decodeResource(ResourceManager.Instance().getResources(), R.drawable.tram);
    public static Bitmap directionIcon =  BitmapFactory.decodeResource(ResourceManager.Instance().getResources(), R.drawable.arrow);

    public static TransportMapPoint CreateTransportPoint(int id) throws IOException
    {
        switch (id)
        {
            case 1:
                return new TransportMapPoint(busIcon, directionIcon);
            case 2:
                return new TransportMapPoint(trolleyIcon,directionIcon);
            case 4:
                return new TransportMapPoint(tramIcon,directionIcon);
            default:
                throw new IOException();
        }
    }
}
