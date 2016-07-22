package com.menshikov.maksim.izhtransport;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.menshikov.maksim.izhtransport.map.IMapPoint;

/**
 * Created by Maksim on 22.07.2016.
 */
public class BusMapPoint implements IMapPoint {

    private final Resources resources;

    private Point point;

    public BusMapPoint()
    {
       resources = ResourceManager.Instance().getResources();
    }

    @Override
    public void setXY(Point point) {

        this.point = point;
    }

    @Override
    public Point getXY() {
        return this.point;
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap busIcon = BitmapFactory.decodeResource(resources, R.drawable.bus);
        Rect drawingRect = new Rect(this.point.x-10,this.point.y-10,this.point.x+10,this.point.y+10);
        canvas.drawBitmap(busIcon,new Rect(0,0,128,128),drawingRect, null);
    }
}
