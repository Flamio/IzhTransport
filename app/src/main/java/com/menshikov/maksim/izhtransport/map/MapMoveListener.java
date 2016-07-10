package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Maksim on 10.07.2016.
 */
public class MapMoveListener implements IMapMoveListener, Observable.OnSubscribe<Bitmap> {
    private MapModel model;
    private IMapView view;
    private Subscriber<? super Bitmap> subscriber = null;
    private boolean isBadMap = false;

    public MapMoveListener(MapModel model, IMapView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onMoving(int dx, int dy) {
        model.setCurrentLeft(model.getCurrentLeft() + dx);
        model.setCurrentTop(model.getCurrentTop() + dy);
        view.clearTransportPoints();
        this.isBadMap = true;
        this.call(this.subscriber);
    }

    @Override
    public void onStopMoving() throws InterruptedException {
        this.isBadMap = false;
        this.call(this.subscriber);
    }

    @Override
    public void onScaling(boolean increase, int centerX, int centerY) {

        int newLeftOffset = model.getCurrentWidth() / 100;
        int newTopOffset = model.getCurrentHeight() / 100;

        int sign = 0;
        if (increase) {
            sign = 1;
        } else {
            sign = -1;
        }
        int newWidth = model.getCurrentWidth() - sign * newLeftOffset;
        int newHeight = model.getCurrentHeight() - sign * newTopOffset;
        model.setCurrentHeight(newHeight);
        model.setCurrentWidth(newWidth);
        model.setCurrentLeft(model.getCurrentLeft() + sign * newLeftOffset);
        model.setCurrentTop(model.getCurrentTop() + sign * newTopOffset);
        this.isBadMap = true;
        this.call(this.subscriber);
        view.clearTransportPoints();
    }

    @Override
    public void call(Subscriber<? super Bitmap> subscriber) {
        if (this.subscriber == null)
            this.subscriber = subscriber;
        subscriber.onNext(model.getMap(this.isBadMap));
    }
}
