package com.menshikov.maksim.izhtransport.Transport;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.menshikov.maksim.izhtransport.R;
import com.menshikov.maksim.izhtransport.ResourceManager;
import com.menshikov.maksim.izhtransport.map.MapPoint;
import com.menshikov.maksim.izhtransport.map.MoveableMapPoint;

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

    public static MoveableMapPoint createTransportPoint(int id) throws IOException
    {
        switch (id)
        {
            case 1:
                return new BusMapPoint(busIcon,directionIcon);
            case 2:
                return new TrolleyMapPoint(trolleyIcon,directionIcon);
            case 4:
                return new TramMapPoint(tramIcon,directionIcon);
            default:
                throw new IOException();
        }
    }
}
