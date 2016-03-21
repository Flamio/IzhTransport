package com.menshikov.maksim.izhtransport;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Maksim on 05.02.2016.
 */
public class MapView extends View implements View.OnTouchListener, IMapView
{
    private Bitmap bitmap;
    private IMapMoveListener mapMoveListener;
    private float beginX;
    private float beginY;
    private float beginGestureLine;
    private ArrayList<Point> points;

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

        if(points == null)
        {
            return;
        }
        for(int i = 0;i<points.size();i++)
        {
            canvas.drawRect(points.get(i).x-10,points.get(i).y-10,points.get(i).x+10,points.get(i).y+10,new Paint(Paint.ANTI_ALIAS_FLAG));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int touches = event.getPointerCount();
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_POINTER_DOWN: // последующие касания
                if (touches ==2)
                {
                    beginGestureLine = (float)Math.sqrt( Math.pow(event.getX(1)-event.getX(0),2.0)+Math.pow(event.getY(1)-event.getY(0),2.0));
                    Log.d("2touch",Float.toString(beginGestureLine) + "begin");
                }
                break;

            case MotionEvent.ACTION_DOWN: // нажатие
                beginX = event.getX();
                beginY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE: // движение

                if (touches ==2)
                {
                    float tempGestureLine = (float)Math.sqrt( Math.pow(event.getX(1)-event.getX(0),2.0)+Math.pow(event.getY(1)-event.getY(0),2.0));
                    int centerX =(int)Math.round(event.getX(1)+event.getX(0)/(float)2);
                    int centerY =(int)Math.round(event.getY(1)+event.getY(0)/(float)2);

                    Log.d("2touch",Float.toString(tempGestureLine));
                    if(tempGestureLine > beginGestureLine)
                    {
                        mapMoveListener.onScaling(true,centerX,centerY);
                    }
                    else if (tempGestureLine < beginGestureLine)
                    {
                        mapMoveListener.onScaling(false,centerX,centerY);
                    }
                    break;
                }

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

    @Override
    public void setTransportPoints(ArrayList<Point> points)
    {
        this.points = points;
    }

    @Override
    public void clearTransportPoints()
    {
        if (points!=null)
        {
            points.clear();
        }
    }
}
