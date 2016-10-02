package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.menshikov.maksim.izhtransport.Sources.TransportInfoSource;
import com.menshikov.maksim.izhtransport.Transport.TransportParser;
import com.menshikov.maksim.izhtransport.map.MapPoint;
import com.menshikov.maksim.izhtransport.map.MapPresenter;
import com.menshikov.maksim.izhtransport.map.MapView;
import com.menshikov.maksim.izhtransport.map.IMapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.unsafe.MpmcArrayQueue;
import rx.schedulers.Schedulers;


public class MapActivity extends Activity
{
    private Subscription subscription;
    private TransportParser transportParser;
    private MapPresenter mapPresenter;
    private int followingTransportID = 0;
    private int followingTransportIndex = 0;
    private ArrayList<MapPoint> points;
    private boolean followingTransport = false;
    private boolean firstLoad;

    @Override
    protected void onResume()
    {
        super.onResume();
        this.subscription = this.subscribeTransportObservable(this.getTransportIntervalObservable());
        firstLoad = true;
        this.subscribeTransportObservable(this.getTransportObservable());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        this.subscription.unsubscribe();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        setContentView(R.layout.maplayout);

        this.bindButtons();

        ResourceManager.Instance().setContext(this.getApplicationContext());

        final IMapView mapView = (MapView) findViewById(R.id.map_view);

        this.mapPresenter = new MapPresenter(mapView, width, height);

        TransportInfoSource transportInfoSource = new TransportInfoSource();
        Intent intent = getIntent();

        int transportType = intent.getIntExtra("TRANSPORT_TYPE", 0) == -1 ? 0 : intent.getIntExtra("TRANSPORT_TYPE", 0);
        int transportNumber = Integer.parseInt(intent.getStringExtra("TRANSPORT_NUMBER"));

        transportInfoSource.setTransportParameters(transportType, transportNumber);
        this.transportParser = new TransportParser(transportInfoSource);

        try
        {
            this.followingTransportID = savedInstanceState.getInt("FOLLOWING_ITEM_ID");
            this.followingTransport = savedInstanceState.getBoolean("IS_FOLLOWING_ITEM");
        }
        catch (NullPointerException ex)
        {
            this.followingTransportID = 0;
            this.followingTransport = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        outstate.putInt("FOLLOWING_ITEM_ID", this.followingTransportID);
        outstate.putBoolean("IS_FOLLOWING_ITEM", this.followingTransport);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.subscription.unsubscribe();
    }

    private Subscription subscribeTransportObservable(Observable transportObservable)
    {
        return transportObservable.filter(new Func1<ArrayList<MapPoint>, Boolean>()
        {
            @Override
            public Boolean call(ArrayList<MapPoint> mapPoints)
            {
                if (mapPoints == null)
                {
                    Handler handler = mapPresenter.getMainLoopHandler();
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Toast toast = Toast.makeText(MapActivity.this, "Не удалось получить данные о транспорте", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                    return false;
                }
                points = mapPoints;
                if (followingTransportID == 0)
                    followingTransportID = points.get(0).getId();
                return true;
            }
        }).subscribe(new Action1<ArrayList<MapPoint>>()
        {
            @Override
            public void call(ArrayList<MapPoint> mapPoints)
            {
                mapPresenter.setMapPoints(mapPoints);
                if (followingTransport || firstLoad)
                {
                    MapPoint point = getMapPointById(followingTransportID);
                    if (point != null)
                    {
                        mapPresenter.moveMapTo(point);
                        firstLoad = false;
                    }
                }
            }
        });
    }

    private MapPoint getMapPointById(int id)
    {
        if (points == null)
            return null;
        for (MapPoint point : this.points)
            if (point.getId() == id)
                return point;
        return null;
    }

    private Observable getTransportObservable()
    {
        return Observable.create(new Observable.OnSubscribe<ArrayList<MapPoint>>()
        {
            @Override
            public void call(Subscriber<? super ArrayList<MapPoint>> subscriber)
            {
                subscriber.onNext(fetchTransport());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());
    }

    private Observable getTransportIntervalObservable()
    {
        return Observable.interval(10, TimeUnit.SECONDS, Schedulers.newThread()).map(new Func1<Long, ArrayList<MapPoint>>()
        {
            @Override
            public ArrayList<MapPoint> call(Long integer)
            {
                return fetchTransport();
            }
        });
    }

    private ArrayList<MapPoint> fetchTransport()
    {
        try
        {
            return this.transportParser.getTransportPositions();
        } catch (InterruptedException e)
        {
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void bindButtons()
    {
        Button selectTransportButton = (Button) findViewById(R.id.select_transport);
        selectTransportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getBaseContext(), SelectTransportActivity.class);
                startActivity(i);
            }
        });

        Button nextTransportButton = (Button) findViewById(R.id.next_transport);
        nextTransportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (points == null)
                    return;
                MapPoint point = points.get(followingTransportIndex);
                followingTransportID = point.getId();
                mapPresenter.moveMapTo(point);

                followingTransportIndex++;
                if (followingTransportIndex >= points.size())
                    followingTransportIndex = 0;
            }
        });

        final Button followingButton = (Button) findViewById(R.id.folow_transport);
        followingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                followingTransport = !followingTransport;
                int drawable = boolToReourceIdConverter(followingTransport);

                followingButton.setBackgroundResource(drawable);
            }
        });
    }

    private int boolToReourceIdConverter(boolean bool)
    {
        return bool ? R.drawable.following_on : R.drawable.following_off;
    }

}
