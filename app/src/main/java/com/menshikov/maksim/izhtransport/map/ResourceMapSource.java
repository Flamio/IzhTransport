package com.menshikov.maksim.izhtransport.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import com.menshikov.maksim.izhtransport.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Maksim on 17.02.2016.
 */
public class ResourceMapSource implements IMapSource
{
    private Resources resources;
    private static BitmapRegionDecoder decoder = null;
    private static int mapWidth = 0;
    private static int mapHeight = 0;
    private Bitmap bitmap;

    public ResourceMapSource(Resources resources)
    {
        this.resources = resources;
        if (decoder == null)
            generateDecoder();
        if (mapWidth == 0 || mapWidth == 0)
            getMapParameters();
    }

    private void getMapParameters()
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.map, options);

        mapHeight = options.outHeight;
        mapWidth = options.outWidth;
    }

    private void generateDecoder()
    {
        try
        {
            InputStream is = resources.openRawResource(+R.drawable.map);
            decoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getHeight()
    {
        return mapHeight;
    }

    @Override
    public int getWidth()
    {
        return mapWidth;
    }


    @Override
    public Bitmap getMap(Rect rect, int screenWidth, int screenHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inPreferQualityOverSpeed = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = decoder.decodeRegion(rect, options);
        return Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, false);
    }
}
