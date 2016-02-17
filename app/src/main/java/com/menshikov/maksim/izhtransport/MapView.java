package com.menshikov.maksim.izhtransport;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Maksim on 05.02.2016.
 */
public class MapView extends View implements View.OnTouchListener, IMapView
{
    private Bitmap bitmap;
    private IMapMoveListener mapMoveListener;
    private float beginX;
    private float beginY;

    public MapView(Context context)
    {
        super(context);
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (bitmap!=null)
        {
            canvas.drawBitmap(bitmap ,0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int touches = event.getPointerCount();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                beginX = event.getX();
                beginY = event.getY();



                break;
            case MotionEvent.ACTION_MOVE: // движение

                int rx = (int)(beginX - event.getX());
                int ry = (int)(beginY -event.getY());
                mapMoveListener.onMoving(rx,ry);

                beginX = event.getX();
                beginY = event.getY();

                break;
            case MotionEvent.ACTION_UP: // отпускание
                mapMoveListener.onStopMoving();
               break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return true;
    }

    @Override
    public void setBitmap(Bitmap _bitmap)
    {
        bitmap = _bitmap;
        invalidate();
    }

    @Override
    public void setMapMoveListener(IMapMoveListener _mapMoveListener)
    {
        mapMoveListener = _mapMoveListener;
    }
}
