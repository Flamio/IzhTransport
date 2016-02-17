package com.menshikov.maksim.izhtransport;

/**
 * Created by Maksim on 17.02.2016.
 */
public class MapPresenter
{

    private MapModel model = null;
    private IMapView view = null;

    public MapPresenter(MapModel _model, IMapView _view)
    {
        model = _model;
        view =  _view;
        view.setMapMoveListener(new IMapMoveListener() {
            @Override
            public void onMoving(int dx, int dy)
            {

            }

            @Override
            public void onStopMoving()
            {

            }

            @Override
            public void onScaling(boolean increase)
            {

            }
        });
        view.setBitmap(model.getMap());
    }

}
