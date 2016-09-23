package com.menshikov.maksim.izhtransport.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import com.menshikov.maksim.izhtransport.R;

/**
 * Created by Maksim on 22.07.2016.
 */
public abstract class MapPoint implements ICloneable
{
    private int id;

    protected final Bitmap bitmap;

    protected Point point;
    protected Location location;

    public MapPoint(Bitmap resources)
    {
        this.bitmap = resources;
    }

    public void setXY(Point point)
    {

        this.point = point;
    }

    public Point getXY()
    {
        return this.point;
    }

    public void setGeoLocation(Location location)
    {
        this.location = location;
    }

    public Location getGeoLocation()
    {
        return this.location;
    }

    public void draw(Canvas canvas)
    {
        Rect drawingRect = new Rect(this.point.x - 10, this.point.y - 10, this.point.x + 10, this.point.y + 10);
        canvas.drawBitmap(this.bitmap, new Rect(0, 0, 128, 128), drawingRect, null);
    }

    abstract public ICloneable clone();

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
