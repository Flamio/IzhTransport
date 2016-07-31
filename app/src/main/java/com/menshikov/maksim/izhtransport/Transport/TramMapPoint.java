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
import com.menshikov.maksim.izhtransport.map.MoveableMapPoint;

/**
 * Created by Maksim on 29.07.2016.
 */
public class TramMapPoint extends MoveableMapPoint
{


    public TramMapPoint(Bitmap bitmap, Bitmap directionIcon)
    {
        super(bitmap, directionIcon);
    }

    @Override
    public ICloneable clone()
    {
        TramMapPoint clonePoint = new TramMapPoint(this.bitmap, this.directionIcon);
        clonePoint.setXY(new Point(this.getXY()));
        clonePoint.setDegreeDirection(this.getDegreeDirection());

        Location cloneLocation = new Location("izh");
        cloneLocation.setLatitude(this.getGeoLocation().getLatitude());
        cloneLocation.setLongitude(this.getGeoLocation().getLongitude());
        clonePoint.setGeoLocation(cloneLocation);
        return clonePoint;
    }
}
