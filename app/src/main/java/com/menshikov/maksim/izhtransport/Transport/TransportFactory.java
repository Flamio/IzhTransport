package com.menshikov.maksim.izhtransport.Transport;

import com.menshikov.maksim.izhtransport.ResourceManager;
import com.menshikov.maksim.izhtransport.map.MapPoint;

/**
 * Created by Maksim on 29.07.2016.
 */
public class TransportFactory
{
    public static MapPoint createTransportPoint(int id)
    {
        switch (id)
        {
            case 1:
                return new BusMapPoint(ResourceManager.Instance().getResources());
            case 2:
                return new TrolleyMapPoint(ResourceManager.Instance().getResources());
            case 4:
                return new TramMapPoint(ResourceManager.Instance().getResources());
        }

        return null;
    }
}
