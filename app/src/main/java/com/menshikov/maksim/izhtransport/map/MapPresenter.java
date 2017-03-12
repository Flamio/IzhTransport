package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Maksim on 29.07.2016.
 */
public class MapPresenter
{
    final private IMapView mapView;
    final MapModel model;
    private final IMapMoveListener mapMoveListener;
    private int width;
    private int height;
    Handler handler = new Handler(Looper.getMainLooper());
    IMapSource mapSource;
    Subscription mapListenerSubscription;
    Scheduler mapListenerSubscribeThread;

    public Handler getMainLoopHandler()
    {
        return this.handler;
    }

    public MapPresenter(final IMapView mapView, IMapSource mapSource, int width, int height)
    {
        this.mapView = mapView;
        this.width = width;
        this.height = height;
        this.mapSource = mapSource;
        this.mapListenerSubscribeThread = Schedulers.newThread();

        model = new MapModel(width, height, mapSource);

        mapMoveListener = new MapMoveListener(model);
        if (mapView != null)
            mapView.setMapMoveListener(mapMoveListener);

        CreateMapListenerSubscription();
        this.StopMapMoving();
    }

    private void StopMapMoving()
    {
        try
        {
            this.mapMoveListener.onStopMoving();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void CreateMapListenerSubscription()
    {
        SubscriptionBuilder<Bitmap> builder = new SubscriptionBuilder();
        this.mapListenerSubscription = builder.Build(new Observable.OnSubscribe<Bitmap>()
        {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber)
            {
                mapMoveListener.setMapSubscriber(subscriber);
            }
        }, this.mapListenerSubscribeThread, AndroidSchedulers.mainThread(), new Subscriber<Bitmap>()
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
                SetBitmapAndVisiblePointsToMapView(bitmap);
            }
        });
    }

    private void SetBitmapAndVisiblePointsToMapView(Bitmap bitmap)
    {
        mapView.setXYMap(0, 0);
        mapView.setBitmap(bitmap);
        ArrayList<MapPoint> points = model.getVisiblePoints();
        mapView.setMapPoints(points);
        mapView.redraw(true);
    }

    public void moveMapTo(MapPoint point)
    {
        if (point == null)
            return;

        if (point.getGeoLocation() != null)
            CoordHelper.addMapPointCoords(point);

        this.model.setCurrentTop(point.getXY().y - height / 2);
        this.model.setCurrentLeft(point.getXY().x - width / 2);
        this.StopMapMoving();
    }

    public void setMapPoints(ArrayList<MapPoint> mapPoints)
    {
        if (mapPoints.isEmpty())
            return;
        model.setMapPoints(mapPoints);
        ArrayList<MapPoint> points = model.getVisiblePoints();
        mapView.setMapPoints(points);
        this.Redraw();
    }

    public void MoveMapToCenter()
    {
        MapPoint centerPoint = new MapPoint(null);
        centerPoint.setXY(new Point(mapSource.getWidth() / 2, mapSource.getHeight() / 2));
        this.moveMapTo(centerPoint);
    }

    private void Redraw()
    {
        this.handler.post(new Runnable()
        {
            public void run()
            {
                mapView.redraw(true);
            }
        });
    }
}
