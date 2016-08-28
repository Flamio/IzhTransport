package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MapActivity extends Activity
{
    private TransportFetcher transportFetcher;
    private MapPresenter mapPresenter;
    private Observable<ArrayList<MapPoint>> fetchTransport;
    private Subscriber<ArrayList<MapPoint>> subscriber;
    private Subscription subscription;

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

        int transportType = intent.getIntExtra("TRANSPORT_TYPE",0);
        int transportNumber = Integer.parseInt(intent.getStringExtra("TRANSPORT_NUMBER"));

        transportInfoSource.setTransportParameters(transportType, transportNumber);
        this.transportFetcher = new TransportFetcher(new TransportParser(transportInfoSource));
        this.fetchTransport = Observable.create(transportFetcher);

        this.subscriber = new Subscriber<ArrayList<MapPoint>>()
        {
            private Toast toast = Toast.makeText(MapActivity.this, "Не удалось получить данные о транспорте", Toast.LENGTH_SHORT);

            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
            }

            @Override
            public void onNext(ArrayList<MapPoint> iMapPoints)
            {
                if (iMapPoints == null)
                {
                    this.toast.show();
                    return;
                }
                mapPresenter.setMapPoints(iMapPoints);
            }
        };

        this.subscription = fetchTransport.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(this.subscriber);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.transportFetcher.stop();
        this.subscription.unsubscribe();
    }

}
