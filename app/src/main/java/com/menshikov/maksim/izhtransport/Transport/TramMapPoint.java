package com.menshikov.maksim.izhtransport.Transport;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import com.menshikov.maksim.izhtransport.R;
import com.menshikov.maksim.izhtransport.map.ICloneable;
import com.menshikov.maksim.izhtransport.map.MapPoint;

/**
 * Created by Maksim on 29.07.2016.
 */
public class TramMapPoint extends MapPoint
{
    public TramMapPoint(Resources resources)
    {
        super(resources);
    }

    @Override
    public void draw(Canvas canvas)
    {
        Bitmap icon = BitmapFactory.decodeResource(resources, R.drawable.tram);
        Rect drawingRect = new Rect(this.point.x - 10, this.point.y - 10, this.point.x + 10, this.point.y + 10);
        canvas.drawBitmap(icon, new Rect(0, 0, 128, 128), drawingRect, null);
    }

    @Override
    public ICloneable clone()
    {
        TramMapPoint clonePoint = new TramMapPoint(this.resources);
        clonePoint.setXY(new Point(this.getXY()));

        Location cloneLocation = new Location("izh");
        cloneLocation.setLatitude(this.getGeoLocation().getLatitude());
        cloneLocation.setLongitude(this.getGeoLocation().getLongitude());
        clonePoint.setGeoLocation(cloneLocation);
        return clonePoint;
    }
}
