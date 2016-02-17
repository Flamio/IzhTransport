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
public class MapModel
{
    private final int screenWidth;
    private final int screenHeight;
    private Resources resources;

    private int mapWidth;
    private int mapHeight;

    private float kx;
    private float ky;

    private int currentLeft = 0;
    private int currentTop = 0;
    private int currentWidth;
    private int currentHeight;

    private Bitmap generalMap;
    private BitmapRegionDecoder decoder;

    public MapModel(int _screenWidth, int _screenHeight, Resources resources)
    {
        screenWidth = _screenWidth;
        screenHeight = _screenHeight;
        this.resources = resources;

        generateGeneralMap();
        generateDecoder();

        currentHeight = screenHeight;
        currentWidth = screenWidth;

    }

    public Bitmap getMap()
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap bitmap = decoder.decodeRegion(new Rect(currentLeft, currentTop, currentLeft + currentWidth, currentTop + currentHeight), options);
        return Bitmap.createScaledBitmap(bitmap,screenWidth,screenHeight,false);
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

    private void generateGeneralMap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.map,options);

        mapHeight = options.outHeight;
        mapWidth = options.outWidth;

        options.inJustDecodeBounds = false;
        options.inSampleSize = 6;
        generalMap = BitmapFactory.decodeResource(resources, R.drawable.map,options);

        kx = generalMap.getWidth() / mapWidth;
        ky = generalMap.getHeight() / mapHeight;
    }

}
