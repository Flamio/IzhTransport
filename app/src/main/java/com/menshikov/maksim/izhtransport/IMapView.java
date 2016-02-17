package com.menshikov.maksim.izhtransport;

import android.graphics.Bitmap;

/**
 * Created by Maksim on 17.02.2016.
 */
public interface IMapView
{
    void setBitmap(Bitmap bitmap);
    void setMapMoveListener(IMapMoveListener mapMoveListener);
}
