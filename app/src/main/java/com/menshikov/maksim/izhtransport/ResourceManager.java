package com.menshikov.maksim.izhtransport;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Maksim on 22.07.2016.
 */
public class ResourceManager {
    private static Context context;
    private static ResourceManager instance;

    private ResourceManager() {
    }

    public static ResourceManager Instance() {
        if (instance == null)
            instance = new ResourceManager();
        return instance;
    }

    public static Resources getResources() {
        return context.getResources();
    }

    public static void setContext(Context c) {
        context = c;
    }
}
