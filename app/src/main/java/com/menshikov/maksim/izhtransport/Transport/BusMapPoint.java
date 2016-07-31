package com.menshikov.maksim.izhtransport.Transport;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;

import com.menshikov.maksim.izhtransport.R;
import com.menshikov.maksim.izhtransport.map.MoveableMapPoint;

/**
 * Created by Maksim on 22.07.2016.
 */
public class BusMapPoint extends MoveableMapPoint
{
    public BusMapPoint(Bitmap bitmap, Bitmap rotateIcon)
    {
        super(bitmap,rotateIcon);
    }

    @Override
    public BusMapPoint clone()
    {
        BusMapPoint clonePoint = new BusMapPoint(this.bitmap,this.directionIcon);
        clonePoint.setXY(new Point(this.getXY()));

        Location cloneLocation = new Location("izh");
        cloneLocation.setLatitude(this.getGeoLocation().getLatitude());
        cloneLocation.setLongitude(this.getGeoLocation().getLongitude());
        clonePoint.setGeoLocation(cloneLocation);
        return clonePoint;
    }
}
