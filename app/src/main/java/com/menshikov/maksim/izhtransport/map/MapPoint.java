package com.menshikov.maksim.izhtransport.map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.menshikov.maksim.izhtransport.ResourceManager;

/**
 * Created by Maksim on 22.07.2016.
 */
public class MapPoint
{
    private int id;
    protected final Bitmap bitmap;

    protected Point point;
    protected Location location;

    protected final float sizeTransportIconFromCenterInDp = 7;

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

    public void draw(Canvas canvas) {


        int sizeTransportIconFromCenterInPixels = (int) this.dipToPixels(ResourceManager.Instance().getResources(), sizeTransportIconFromCenterInDp);
        int bitmapSizeInPixels = (int) this.dipToPixels(ResourceManager.Instance().getResources(), this.bitmap.getHeight());

        Rect drawingRect = new Rect(this.point.x - sizeTransportIconFromCenterInPixels, this.point.y - sizeTransportIconFromCenterInPixels, this.point.x + sizeTransportIconFromCenterInPixels, this.point.y + sizeTransportIconFromCenterInPixels);
        canvas.drawBitmap(this.bitmap, new Rect(0, 0, bitmapSizeInPixels, bitmapSizeInPixels), drawingRect, null);
    }

    public MapPoint Clone()
    {
        MapPoint clonePoint = new MapPoint(this.bitmap);
        clonePoint.setXY(new Point(this.getXY()));
        Location cloneLocation = new Location("izh");
        cloneLocation.setLatitude(this.getGeoLocation().getLatitude());
        cloneLocation.setLongitude(this.getGeoLocation().getLongitude());
        clonePoint.setGeoLocation(cloneLocation);
        return clonePoint;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    protected static float dipToPixels(Resources res, float dipValue) {
        DisplayMetrics metrics = res.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
