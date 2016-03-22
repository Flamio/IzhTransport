package com.menshikov.maksim.izhtransport;

import java.io.IOException;

/**
 * Created by Maksim on 17.02.2016.
 */
public interface IMapMoveListener
{
    void onMoving(int dx,int dy);
    void onStopMoving() throws IOException, InterruptedException;
    void onScaling(boolean increase, int centerX,int centerY);
}
