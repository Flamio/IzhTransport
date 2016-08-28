package com.menshikov.maksim.izhtransport.Transport;

import com.menshikov.maksim.izhtransport.map.CoordHelper;
import com.menshikov.maksim.izhtransport.map.MapPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Maksim on 22.07.2016.
 */
public class TransportFetcher implements Observable.OnSubscribe<ArrayList<MapPoint>>
{

    private TransportParser transportParser;
    private Subscriber<? super ArrayList<MapPoint>> subscriber;
    private long updateInterval = 5000;

    private TimerTask timerTask = new TimerTask()
    {
        @Override
        public void run()
        {
            if (subscriber == null)
                return;
            call(subscriber);
        }
    };

    private Timer timer = new Timer();

    public TransportFetcher(TransportParser transportParser)
    {
        this.transportParser = transportParser;
    }

    public void stop()
    {
        this.timer.purge();
    }

    @Override
    public void call(Subscriber<? super ArrayList<MapPoint>> subscriberPar)
    {
        if (this.subscriber == null)
        {
            this.subscriber = subscriberPar;
            timer.schedule(this.timerTask, this.updateInterval, this.updateInterval);
        }

        try
        {
            ArrayList<MapPoint> mapPoints = transportParser.getTransportPositions();
            for (MapPoint mapPoint : mapPoints)
                CoordHelper.addMapPointCoords(mapPoint);

            this.subscriber.onNext(mapPoints);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
            this.subscriber.onNext(null);
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
            this.subscriber.onNext(null);
        }

    }
}
