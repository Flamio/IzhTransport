package com.menshikov.maksim.izhtransport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.menshikov.maksim.izhtransport.map.IMapMoveListener;
import com.menshikov.maksim.izhtransport.map.IMapPoint;
import com.menshikov.maksim.izhtransport.map.IMapView;

import java.io.IOException;
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
    private ArrayList<IMapPoint> points;
    private int xMap = 0;
    private int yMap = 0;
    private boolean isDrawMovingPoints = true;

    public void setXYMap(int x, int y)
    {
        this.xMap = x;
        this.yMap = y;
    }

    @Override
    public Point getXYMap()
    {
        return new Point(this.xMap, this.yMap);
    }

    @Override
    public void redraw(boolean isDrawingTransport)
    {
        this.isDrawMovingPoints = isDrawingTransport;
        invalidate();
    }

    public MapView(Context context)
    {
        super(context);
        setOnTouchListener(this);
    }

    public MapView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public MapView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setOnTouchListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        if (bitmap != null)
        {
            canvas.drawBitmap(bitmap, xMap, yMap, new Paint(Paint.ANTI_ALIAS_FLAG));
        }

        if (!this.isDrawMovingPoints)
        {
            this.isDrawMovingPoints = true;
            return;
        }

        if (points == null)
        {
            return;
        }
        for (int i = 0; i < points.size(); i++)
        {
            this.points.get(i).draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int touches = event.getPointerCount();
        switch (event.getActionMasked())
        {

            case MotionEvent.ACTION_POINTER_DOWN: // последующие касания
                if (touches == 2)
                {
                    beginGestureLine = (float) Math.sqrt(Math.pow(event.getX(1) - event.getX(0), 2.0) + Math.pow(event.getY(1) - event.getY(0), 2.0));
                    Log.d("2touch", Float.toString(beginGestureLine) + "begin");
                }
                break;

            case MotionEvent.ACTION_DOWN: // нажатие
                beginX = event.getX();
                beginY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE: // движение

                if (touches == 2)
                {
                    float tempGestureLine = (float) Math.sqrt(Math.pow(event.getX(1) - event.getX(0), 2.0) + Math.pow(event.getY(1) - event.getY(0), 2.0));
                    int centerX = (int) Math.round(event.getX(1) + event.getX(0) / (float) 2);
                    int centerY = (int) Math.round(event.getY(1) + event.getY(0) / (float) 2);

                    Log.d("2touch", Float.toString(tempGestureLine));
                    if (tempGestureLine > beginGestureLine)
                    {
                        mapMoveListener.onScaling(true, centerX, centerY);
                    } else if (tempGestureLine < beginGestureLine)
                    {
                        mapMoveListener.onScaling(false, centerX, centerY);
                    }
                    break;
                }

                int rx = (int) (beginX - event.getX());
                int ry = (int) (beginY - event.getY());
                mapMoveListener.onMoving(rx, ry);

                beginX = event.getX();
                beginY = event.getY();

                break;
            case MotionEvent.ACTION_UP: // отпускание
                try
                {
                    mapMoveListener.onStopMoving();
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
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
    public void setMapPoints(ArrayList<IMapPoint> points)
    {
        this.points = points;
        this.invalidate();
    }

    @Override
    public void clearTransportPoints()
    {
        if (points != null)
        {
            points.clear();
        }
    }
}
