package com.menshikov.maksim.izhtransport;

/**
 * Created by Maksim on 27.02.2016.
 */
public interface ITransportInfoSource
{
    void setTransportParameters(int transportId, int number);
    String getServerResponse();
}
