package com.menshikov.maksim.izhtransport;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.test.ApplicationTestCase;

import com.menshikov.maksim.izhtransport.map.IMapSource;
import com.menshikov.maksim.izhtransport.map.MapModel;
import com.menshikov.maksim.izhtransport.map.MapPresenter;

import java.lang.reflect.Field;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class GraphicMapTest extends ApplicationTestCase<Application>
{
    public GraphicMapTest()
    {
        super(Application.class);
    }

    private MapModel mapModel;
    private MapPresenter mapPresenter;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    public void testInitialRasterPlace() throws NoSuchFieldException, IllegalAccessException
    {

        IMapSource mapSource = new IMapSource()
        {
            @Override
            public int getHeight()
            {
                return 1000;
            }

            @Override
            public int getWidth()
            {
                return 1000;
            }

            @Override
            public Bitmap getMap(Rect rect, int screenWidth, int screenHeight)
            {
                return null;
            }
        };

        this.mapPresenter = new MapPresenter(null, mapSource, 100, 100);
        this.mapModel = new MapModel(100, 100, mapSource);

        Field f = this.mapPresenter.getClass().getDeclaredField("model");
        f.setAccessible(true);
        this.mapModel = (MapModel)f.get(this.mapPresenter);

        assertEquals(this.mapModel.getCurrentLeft(), 450);
        assertEquals(this.mapModel.getCurrentTop(), 450);
    }

}