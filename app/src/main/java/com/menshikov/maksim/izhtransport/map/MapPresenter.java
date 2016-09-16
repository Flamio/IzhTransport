package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.menshikov.maksim.izhtransport.ResourceManager;

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
    final MapModel model;
    private int width;
    private int height;

    public MapPresenter(final IMapView mapView, int width, int height)
    {
        this.mapView = mapView;
        this.width = width;
        this.height = height;

        model = new MapModel(width, height, new ResourceMapSource(ResourceManager.Instance().getResources()));

        MapMoveListener mapMoveListener = new MapMoveListener(model, mapView);

        Observable<Bitmap> fetchMap = Observable.create(mapMoveListener);

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
    }

    public void setMapPoints(ArrayList<MapPoint> mapPoints)
    {
        if (mapPoints.isEmpty())
            return;
        model.setMapPoints(mapPoints);
        ArrayList<MapPoint> points = model.getVisiblePoints();
        mapView.setMapPoints(points);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            public void run()
            {
                mapView.redraw(true);
            }
        });

    }

}
