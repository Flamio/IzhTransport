package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;


import rx.Subscriber;

/**
 * Created by Maksim on 10.07.2016.
 */
public class MapMoveListener implements IMapMoveListener
{
    private MapModel model;
    private Subscriber<? super Bitmap> onStopSubscriber = null;

    private Subscriber<? super Boolean> onDrawingMoveablesListener;

    public MapMoveListener(MapModel model)
    {
        this.model = model;

    }

    @Override
    public void onMoving(int dx, int dy)
    {
        model.setCurrentLeft(model.getCurrentLeft() + dx);
        model.setCurrentTop(model.getCurrentTop() + dy);

        this.onDrawingMoveablesListener.onNext(false);
    }

    @Override
    public void onStopMoving() throws InterruptedException
    {
        this.onDrawingMoveablesListener.onNext(true);
        this.onStopSubscriber.onNext(model.getMap());
    }

    @Override
    public void onScaling(boolean increase, int centerX, int centerY)
    {

       /* int newLeftOffset = model.getCurrentWidth() / 100;
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
        if (!this.isTimerRunning) {
            handler.postDelayed(handlerRunnable, this.updateInterval);
            this.isTimerRunning = true;
        }
        //this.call(this.onStopSubscriber);
        //view.clearTransportPoints();*/
    }

    @Override
    public void setMapSubscriber(Subscriber<? super Bitmap> subscriber)
    {
        this.onStopSubscriber = subscriber;
    }

    @Override
    public void setDrawingMoveablesListener(Subscriber<? super Boolean> subscriber)
    {
        this.onDrawingMoveablesListener = subscriber;
    }
}
