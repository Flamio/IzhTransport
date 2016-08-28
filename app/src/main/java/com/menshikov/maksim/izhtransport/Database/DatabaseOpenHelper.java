package com.menshikov.maksim.izhtransport.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Maksim on 06.08.2016.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper
{
    public DatabaseOpenHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }
}
