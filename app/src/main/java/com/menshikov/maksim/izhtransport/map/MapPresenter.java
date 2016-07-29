package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;
import android.util.Log;

import com.menshikov.maksim.izhtransport.ResourceManager;
import com.menshikov.maksim.izhtransport.Sources.TransportTestSource;
import com.menshikov.maksim.izhtransport.Transport.TransportFetcher;
import com.menshikov.maksim.izhtransport.Transport.TransportParser;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maksim on 29.07.2016.
 */
public class MapPresenter
{
    final private IMapView mapView;
    private int width;
    private int height;

    public MapPresenter(final IMapView mapView, int width, int height)
    {
        this.mapView = mapView;
        this.width = width;
        this.height = height;

        final MapModel model = new MapModel(width, height, new ResourceMapSource(ResourceManager.Instance().getResources()));

        MapMoveListener mapMoveListener = new MapMoveListener(model, mapView);

        Observable<Bitmap> fetchMap = Observable.create(mapMoveListener);

        TransportFetcher transportFetcher = new TransportFetcher(new TransportParser(new TransportTestSource()));

        Observable<ArrayList<MapPoint>> fetchTransport = Observable.create(transportFetcher);

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

                ArrayList<MapPoint> points = model.getVisiblePoints();
                mapView.setMapPoints(points);
                mapView.redraw(true);
            }
        });


        fetchTransport.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ArrayList<MapPoint>>()
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
            public void onNext(ArrayList<MapPoint> iMapPoints)
            {
                if (iMapPoints.isEmpty())
                    return;
                model.setMapPoints(iMapPoints);
                ArrayList<MapPoint> points = model.getVisiblePoints();
                mapView.setMapPoints(points);
                mapView.redraw(true);
            }
        });

    }

}
