package com.menshikov.maksim.izhtransport.Transport;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import com.menshikov.maksim.izhtransport.R;
import com.menshikov.maksim.izhtransport.map.MapPoint;

/**
 * Created by Maksim on 22.07.2016.
 */
public class BusMapPoint extends MapPoint
{
    public BusMapPoint(Resources resources)
    {
        super(resources);
    }

    @Override
    public void draw(Canvas canvas)
    {
        Bitmap busIcon = BitmapFactory.decodeResource(resources, R.drawable.bus);
        Rect drawingRect = new Rect(this.point.x - 10, this.point.y - 10, this.point.x + 10, this.point.y + 10);
        canvas.drawBitmap(busIcon, new Rect(0, 0, 128, 128), drawingRect, null);
    }

    @Override
    public BusMapPoint clone()
    {
        BusMapPoint clonePoint = new BusMapPoint(this.resources);
        clonePoint.setXY(new Point(this.getXY()));

        Location cloneLocation = new Location("izh");
        cloneLocation.setLatitude(this.getGeoLocation().getLatitude());
        cloneLocation.setLongitude(this.getGeoLocation().getLongitude());
        clonePoint.setGeoLocation(cloneLocation);
        return clonePoint;
    }

}