package com.menshikov.maksim.izhtransport;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Maksim on 22.03.2016.
 */
public class TransportInfoSource implements ITransportInfoSource  {
    @Override
    public void setTransportParameters(int transportId, int number)
    {

    }

    private  static String convertStreamToString( InputStream is, String encoding ) throws IOException
    {
        StringBuilder sb = new StringBuilder( Math.max( 16, is.available() ) );
        char[] tmp = new char[ 4096 ];

        try {
            InputStreamReader reader = new InputStreamReader( is, encoding );
            for( int cnt; ( cnt = reader.read( tmp ) ) > 0; )
                sb.append( tmp, 0, cnt );
        } finally {
            is.close();
        }
        return sb.toString();
    }

    @Override
    public String getServerResponse() throws InterruptedException {
        final String[] response = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try
                {
                    url = new URL("http://map.igis.ru/layers/?param=filter=IMode,=,2&id=215&editor=0&reload=1&&rnd=4719");
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                assert url != null;
                HttpURLConnection connection = null;
                try
                {
                    connection = (HttpURLConnection)url.openConnection();
                }
                catch (IOException e)
                {
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
                    response[0] = convertStreamToString(in,encoding);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();
        thread.join();

        return response[0];
    }


}
