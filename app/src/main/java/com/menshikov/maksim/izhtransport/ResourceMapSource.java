package com.menshikov.maksim.izhtransport;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Maksim on 17.02.2016.
 */
public class ResourceMapSource implements IMapSource
{

    private Resources resources;
    private BitmapRegionDecoder decoder;
    private Bitmap generalMap;
    private float kx;
    private float ky;
    private int mapWidth;
    private int mapHeight;
    private Bitmap bitmap;

    public ResourceMapSource(Resources resources)
    {
        this.resources = resources;
        generateDecoder();
        generateGeneralMap();
    }

    private void generateGeneralMap()
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.map,options);

        mapHeight = options.outHeight;
        mapWidth = options.outWidth;

        options.inJustDecodeBounds = false;
        options.inSampleSize = 15;
        generalMap = BitmapFactory.decodeResource(resources, R.drawable.map,options);
        int width = generalMap.getWidth();
        int height = generalMap.getHeight();
        kx = (float)mapWidth / (float)width;
        ky = (float)mapHeight / (float)height;
    }

    private void generateDecoder()
    {
        try
        {
            InputStream is = resources.openRawResource(+R.drawable.map);
            decoder = BitmapRegionDecoder.newInstance(is, false);
        }
        catch (IOException e)
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
        bitmap = decoder.decodeRegion(rect, options);
        return Bitmap.createScaledBitmap(bitmap,screenWidth,screenHeight,false);
    }

    @Override
    public Bitmap getBadMap(Rect rect, int screenWidth, int screenHeight)
    {
        bitmap = Bitmap.createBitmap(generalMap, (int) (rect.left / kx), (int) (rect.top / ky), (int) (rect.width() / kx), (int) (rect.height() / ky));
        return Bitmap.createScaledBitmap(bitmap,screenWidth,screenHeight,false);
    }
}
