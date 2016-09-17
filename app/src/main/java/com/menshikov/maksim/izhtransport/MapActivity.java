package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.menshikov.maksim.izhtransport.Sources.TransportInfoSource;
import com.menshikov.maksim.izhtransport.Transport.TransportFetcher;
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
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MapActivity extends Activity
{
    private Subscription subscription;
    private TransportParser transportParser;
    private MapPresenter mapPresenter;

    @Override
    protected void onResume()
    {
        super.onResume();
        this.subscription = this.subscribeTransportObservable(this.getTransportObservable());
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

        ResourceManager.Instance().setContext(this.getApplicationContext());

        final IMapView mapView = (MapView) findViewById(R.id.map_view);

        this.mapPresenter = new MapPresenter(mapView, width, height);

        TransportInfoSource transportInfoSource = new TransportInfoSource();
        Intent intent = getIntent();

        int transportType = intent.getIntExtra("TRANSPORT_TYPE", 0) == -1 ? 0 : intent.getIntExtra("TRANSPORT_TYPE", 0);
        int transportNumber = Integer.parseInt(intent.getStringExtra("TRANSPORT_NUMBER"));

        transportInfoSource.setTransportParameters(transportType, transportNumber);
        this.transportParser = new TransportParser(transportInfoSource);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.subscription.unsubscribe();
    }

    private Subscription subscribeTransportObservable(Observable transportObservable)
    {
        return transportObservable.subscribe(new Action1<ArrayList<MapPoint>>()
        {
            @Override
            public void call(ArrayList<MapPoint> mapPoints)
            {
                mapPresenter.setMapPoints(mapPoints);
                mapPresenter.moveMapTo(mapPoints.get(0));
            }
        });
    }

    private Observable getTransportObservable()
    {
        return Observable.interval(10, TimeUnit.SECONDS, Schedulers.newThread()).map(new Func1<Long, ArrayList<MapPoint>>()
        {
            @Override
            public ArrayList<MapPoint> call(Long integer)
            {
                return fetchTransport();
            }
        }).filter(new Func1<ArrayList<MapPoint>, Boolean>()
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
                return true;
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

}
