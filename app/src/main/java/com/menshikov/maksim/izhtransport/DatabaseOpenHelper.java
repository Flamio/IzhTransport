package com.menshikov.maksim.izhtransport;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Maksim on 06.08.2016.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "IzhTransport.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
