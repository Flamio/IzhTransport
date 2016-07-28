package com.menshikov.maksim.izhtransport.Transport;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.os.Handler;

import com.menshikov.maksim.izhtransport.BusMapPoint;
import com.menshikov.maksim.izhtransport.map.IMapPoint;
import com.menshikov.maksim.izhtransport.map.PointConverter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Maksim on 22.07.2016.
 */
public class TransportFetcher implements Observable.OnSubscribe<ArrayList<IMapPoint>>
{

    private TransportParser transportParser;
    private Subscriber<? super ArrayList<IMapPoint>> subscriber;
    private long updateInterval = 1000;

    private Timer timer = new Timer();

    public TransportFetcher(TransportParser transportParser)
    {
        this.transportParser = transportParser;
    }

    @Override
    public void call(Subscriber<? super ArrayList<IMapPoint>> subscriberPar)
    {
        if (this.subscriber == null)
        {
            this.subscriber = subscriberPar;
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (subscriber == null)
                        return;
                    call(subscriber);
                }
            }, updateInterval, updateInterval);
        }

        try
        {
            ArrayList<Location> locations = transportParser.getTransportPositions(0, 0);
            ArrayList<IMapPoint> mapPoints = new ArrayList<IMapPoint>();
            for (int i = 0; i < locations.size(); i++)
            {
                BusMapPoint busPoint = new BusMapPoint();
                PointConverter.convertToMapPoint(locations.get(i), busPoint);
                mapPoints.add(busPoint);
            }
            this.subscriber.onNext(mapPoints);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

   /* public ArrayList<Point> getTransportPoints() throws InterruptedException {
        ArrayList<Location> locations = transportParser.getTransportPositions(0, 0);
        if (locations == null) {
            return null;
        }

        ArrayList<Point> points = new ArrayList<Point>();

        for (int i = 0; i < locations.size(); i++) {
            Point currentPoint = convertLocationToMap(locations.get(i));
            if (isPointInCurrentMapRect(currentPoint)) {
                float kx = (float) currentWidth / screenWidth;
                float ky = (float) currentHeight / screenHeight;

                currentPoint.x /= kx;
                currentPoint.y /= ky;
                currentPoint.x -= (float) getCurrentLeft() / kx;
                currentPoint.y -= (float) getCurrentTop() / ky;

                points.add(currentPoint);
            }
        }
        return points;
    }*/
}
