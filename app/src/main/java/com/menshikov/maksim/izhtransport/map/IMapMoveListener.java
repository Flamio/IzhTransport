package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;

import java.io.IOException;

import rx.Subscriber;

/**
 * Created by Maksim on 17.02.2016.
 */
public interface IMapMoveListener
{
    void onMoving(int dx, int dy);

    void onStopMoving() throws IOException, InterruptedException;

    void onScaling(boolean increase, int centerX, int centerY);

    void setMapSubscriber(Subscriber<? super Bitmap> subscriber);

    void setDrawingMoveablesListener(Subscriber<? super Boolean> subscriber);
}
