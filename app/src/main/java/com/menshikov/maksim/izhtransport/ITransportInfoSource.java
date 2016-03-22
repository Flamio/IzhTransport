package com.menshikov.maksim.izhtransport;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Maksim on 27.02.2016.
 */
public interface ITransportInfoSource
{
    void setTransportParameters(int transportId, int number);
    String getServerResponse() throws InterruptedException;
}
