package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.menshikov.maksim.izhtransport.ResourceManager;

import java.io.IOException;
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
    private final IMapMoveListener mapMoveListener;
    private int width;
    private int height;
    Handler handler = new Handler(Looper.getMainLooper());

    public Handler getMainLoopHandler()
    {
        return this.handler;
    }

    public MapPresenter(final IMapView mapView, IMapSource mapSource, int width, int height)
    {
        this.mapView = mapView;
        this.width = width;
        this.height = height;

        model = new MapModel(width, height, mapSource);

        MapPoint centerPoint = new MapPoint(null)
        {
            @Override
            public ICloneable clone()
            {
                return null;
            }
        };

        centerPoint.setXY(new Point(mapSource.getWidth()/2, mapSource.getHeight()/2));

        mapMoveListener = new MapMoveListener(model);
        if (mapView!= null)
            mapView.setMapMoveListener(mapMoveListener);

        Observable.create(new Observable.OnSubscribe<Bitmap>()
        {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber)
            {
                mapMoveListener.setMapSubscriber(subscriber);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Bitmap>()
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

        this.moveMapTo(centerPoint);
    }

    public void moveMapTo(MapPoint point)
    {
        if (point == null)
            return;

        if (point.getGeoLocation() != null)
            CoordHelper.addMapPointCoords(point);

        this.model.setCurrentTop(point.getXY().y - height / 2);
        this.model.setCurrentLeft(point.getXY().x - width / 2);
        try
        {

            this.mapMoveListener.onStopMoving();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

    }

    public void setMapPoints(ArrayList<MapPoint> mapPoints)
    {
        if (mapPoints.isEmpty())
            return;
        model.setMapPoints(mapPoints);
        ArrayList<MapPoint> points = model.getVisiblePoints();
        mapView.setMapPoints(points);
        this.redraw();
    }

    private void redraw()
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
