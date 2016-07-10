package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.menshikov.maksim.izhtransport.map.MapModel;
import com.menshikov.maksim.izhtransport.map.MapPresenter;
import com.menshikov.maksim.izhtransport.map.MapView;

import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapView mapView = new MapView(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        try {
            MapPresenter mapPresenter = new MapPresenter(new MapModel(width, height,new ResourceMapSource(getResources()), new TransportInfoSource()), mapView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(mapView);
    }



}
