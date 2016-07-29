package com.menshikov.maksim.izhtransport.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import com.menshikov.maksim.izhtransport.R;
import com.menshikov.maksim.izhtransport.ResourceManager;

/**
 * Created by Maksim on 22.07.2016.
 */
public abstract class MapPoint implements ICloneable
{
    protected final Resources resources;

    protected Point point;
    protected Location location;

    public MapPoint(Resources resources)
    {
        this.resources = resources;
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

    abstract public void draw(Canvas canvas);

    abstract public ICloneable clone();
}
