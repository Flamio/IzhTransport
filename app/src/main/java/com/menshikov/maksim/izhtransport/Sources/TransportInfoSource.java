package com.menshikov.maksim.izhtransport.Sources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maksim on 22.03.2016.
 */
public class TransportInfoSource implements ITransportInfoSource {
    @Override
    public void setTransportParameters(int transportId, int number)
    {

    }

    private String convertStreamToString(InputStream is, String encoding) throws IOException {
        if (is == null)
            throw new IOException();
        StringBuilder sb = new StringBuilder(Math.max(16, is.available()));
        char[] tmp = new char[4096];

        try {
            InputStreamReader reader = new InputStreamReader(is, encoding);
            for (int cnt; (cnt = reader.read(tmp)) > 0; )
                sb.append(tmp, 0, cnt);
        } finally {
            is.close();
        }
        return sb.toString();
    }

    @Override
    public String getServerResponse() throws InterruptedException {
        final String[] response = new String[1];
        URL url = null;
        try {
            //url = new URL("http://map.igis.ru/layers/?param=filter=field_reys_nom,=,2*filter=IMode,=,2&id=215&editor=0&reload=1&&rnd=4719");
            url = new URL("http://map.igis.ru/layers/?param=filter=&id=215&editor=0&reload=1&&rnd=4719");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert url != null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream in = null;
        assert connection != null;
        try {
            in = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encoding = connection.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        try {
            response[0] = convertStreamToString(in, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response[0];
    }


}
