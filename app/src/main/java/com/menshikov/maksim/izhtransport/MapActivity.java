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
import com.menshikov.maksim.izhtransport.map.ResourceMapSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
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
        this.subscription = this.subscribeTransportObservable(this.getTransportIntervalObservable());
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

        this.mapPresenter = new MapPresenter(mapView, new ResourceMapSource(ResourceManager.Instance().getResources()), width, height);
        this.mapPresenter.MoveMapToCenter();

        TransportInfoSource transportInfoSource = new TransportInfoSource();
        Intent intent = getIntent();

        int transportType = intent.getIntExtra("TRANSPORT_TYPE", 0) == -1 ? 0 : intent.getIntExtra("TRANSPORT_TYPE", 0);
        int transportNumber = Integer.parseInt(intent.getStringExtra("TRANSPORT_NUMBER"));

        transportInfoSource.setTransportParameters(transportType, transportNumber);
        this.transportParser = new TransportParser(transportInfoSource);
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        /*outstate.putInt("FOLLOWING_ITEM_ID", this.followingTransportID);
        outstate.putBoolean("IS_FOLLOWING_ITEM", this.followingTransport);*/
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
                if (mapPoints != null)
                    return true;

                ShowToast("Не удалось получить данные о транспорте");
                return false;
            }
        }).subscribe(new Action1<ArrayList<MapPoint>>()
        {
            @Override
            public void call(ArrayList<MapPoint> mapPoints)
            {
                mapPresenter.setMapPoints(mapPoints);
            }
        });
    }

    private void ShowToast(final String message)
    {
        Handler handler = mapPresenter.getMainLoopHandler();
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast toast = Toast.makeText(MapActivity.this, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
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
            return this.transportParser.getTransportPoints();
        } catch (IOException e)
        {
            return null;
        }
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

    }
}
