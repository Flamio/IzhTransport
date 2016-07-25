package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.menshikov.maksim.izhtransport.Sources.TransportInfoSource;
import com.menshikov.maksim.izhtransport.Sources.TransportTestSource;
import com.menshikov.maksim.izhtransport.Transport.TransportFetcher;
import com.menshikov.maksim.izhtransport.Transport.TransportParser;
import com.menshikov.maksim.izhtransport.map.IMapPoint;
import com.menshikov.maksim.izhtransport.map.ResourceMapSource;
import com.menshikov.maksim.izhtransport.map.IMapView;
import com.menshikov.maksim.izhtransport.map.MapModel;
import com.menshikov.maksim.izhtransport.map.MapMoveListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MapActivity extends Activity
{

    private ArrayList<IMapPoint> mapPoints;

    private void setVisiblePointsToMap(MapModel model, IMapView mapView)
    {
        if (mapPoints == null)
            return;

        ArrayList<IMapPoint> visiblePoints = new ArrayList<IMapPoint>(mapPoints.size());

        for (IMapPoint mapPoint : mapPoints)
        {
            visiblePoints.add((IMapPoint) mapPoint.clone());
        }

        for (int i = 0; i < visiblePoints.size(); i++)
            if (!model.convertPointToScreenCoord(visiblePoints.get(i)))
                visiblePoints.remove(i);

        mapView.setMapPoints(visiblePoints);
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

        final MapModel model = new MapModel(width, height, new ResourceMapSource(getResources()));

        MapMoveListener mapMoveListener = new MapMoveListener(model, mapView);

        Observable<Bitmap> fetchMap = Observable.create(mapMoveListener);

        TransportFetcher transportFetcher = new TransportFetcher(new TransportParser(new TransportInfoSource()));

        Observable<ArrayList<IMapPoint>> fetchTransport = Observable.interval(2, TimeUnit.SECONDS).timeInterval().create(transportFetcher);

        mapView.setMapMoveListener(mapMoveListener);

        fetchMap.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Bitmap>()
        {


            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                Log.e(e.getMessage(), e.getMessage());
            }

            @Override
            public void onNext(Bitmap bitmap)
            {
                mapView.setXYMap(0, 0);
                mapView.setBitmap(bitmap);

                setVisiblePointsToMap(model, mapView);
            }
        });


        fetchTransport.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ArrayList<IMapPoint>>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                Log.e(e.getMessage(), e.getMessage());
            }

            @Override
            public void onNext(ArrayList<IMapPoint> iMapPoints)
            {
                if (iMapPoints.isEmpty())
                    return;
                mapPoints = iMapPoints;
                setVisiblePointsToMap(model, mapView);
            }
        });

    }

}
