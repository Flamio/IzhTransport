package com.menshikov.maksim.izhtransport.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.menshikov.maksim.izhtransport.ListItem.ListItem;
import com.menshikov.maksim.izhtransport.ListItem.ListLeaf;
import com.menshikov.maksim.izhtransport.ListItem.NodeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 21.08.2016.
 */

public class DatabaseAccess
{
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context)
    {
        this.openHelper = new DatabaseOpenHelper(context, "IzhTransport.db", 1);
    }

    public static DatabaseAccess getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open()
    {
        this.database = openHelper.getWritableDatabase();
    }


    public void close()
    {
        if (database != null)
        {
            this.database.close();
        }
    }

    public ListItem getAllTransportTypes()
    {
        NodeItem itemsTree = new NodeItem(0, "main");
        Cursor cursor = database.rawQuery("SELECT * FROM TransportType", null);
        cursor.moveToFirst();

        ListLeaf nodeAll = new ListLeaf(-1, "Все");
        itemsTree.addChildren(nodeAll);

        while (!cursor.isAfterLast())
        {
            NodeItem transportType = new NodeItem(Integer.parseInt(cursor.getString(2)), cursor.getString(1));

            ListLeaf allOfType = new ListLeaf(-1, "Все");
            transportType.addChildren(allOfType);

            Cursor cursorNumbers = database.rawQuery("select Number, Id  from Transport  where TypeId ==  " + transportType.getIdItemm(), null);
            cursorNumbers.moveToFirst();
            while (!cursorNumbers.isAfterLast())
            {
                ListLeaf transportNumber = new ListLeaf(Integer.parseInt(cursorNumbers.getString(1)), cursorNumbers.getString(0));
                transportType.addChildren(transportNumber);
                cursorNumbers.moveToNext();
            }
            cursorNumbers.close();
            itemsTree.addChildren(transportType);
            cursor.moveToNext();
        }

        cursor.close();
        return itemsTree;
    }
}
