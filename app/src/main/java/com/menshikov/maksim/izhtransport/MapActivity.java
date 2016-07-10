package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.menshikov.maksim.izhtransport.Sources.ResourceMapSource;
import com.menshikov.maksim.izhtransport.Sources.TransportInfoSource;
import com.menshikov.maksim.izhtransport.map.MapModel;
import com.menshikov.maksim.izhtransport.map.MapMoveListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        setContentView(R.layout.maplayout);

        Button selectTransportButton = (Button)findViewById(R.id.select_transport);
        selectTransportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),SelectTransportActivity.class);
                startActivity(i);
            }
        });

        final MapView mapView = (MapView) findViewById(R.id.map_view);

        final MapModel model = new MapModel(width, height, new ResourceMapSource(getResources()), new TransportInfoSource());

        MapMoveListener mapMoveListener = new MapMoveListener(model, mapView);

        Observable<Bitmap> fetchMap = Observable.create(mapMoveListener);
        mapView.setMapMoveListener(mapMoveListener);

        fetchMap.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap s) {
                mapView.setBitmap(s);
            }
        });



    }


}
