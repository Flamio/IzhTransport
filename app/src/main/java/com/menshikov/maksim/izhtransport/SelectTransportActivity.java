package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.menshikov.maksim.izhtransport.Database.DatabaseAccess;
import com.menshikov.maksim.izhtransport.ListItem.ListItem;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action;
import rx.functions.Action1;


public class SelectTransportActivity extends Activity
{
    private ListItem currentListItem;
    private ListView transportTypesList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transport);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        final ListItem transportTypes = databaseAccess.getAllTransportTypes();
        this.currentListItem = transportTypes;

        this.transportTypesList = (ListView) findViewById(R.id.transport_types_list);

        this.context = this;

        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this.context, transportTypes);

        this.transportTypesList.setAdapter(menuItemAdapter);

        ListItem.setLeafAction(new Action1<ListItem>()
        {
            @Override
            public void call(ListItem selectedItem)
            {
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                intent.putExtra("TRANSPORT_TYPE",  selectedItem.getParent().getName());
                intent.putExtra("TRANSPORT_NUMBER", selectedItem.getName() == "Все" ? "0" : selectedItem.getName() );
                startActivity(intent);
            }
        });

        ListItem.setNodeAction(new Action1<ListItem>()
        {
            @Override
            public void call(ListItem selectedItem)
            {
                currentListItem = selectedItem;
                MenuItemAdapter menuItemAdapter = new MenuItemAdapter(context, selectedItem);
                transportTypesList.setAdapter(null);
                transportTypesList.setAdapter(menuItemAdapter);
                return;
            }
        });

        transportTypesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                ListItem selectedItem = (ListItem) adapterView.getItemAtPosition(i);
                selectedItem.operation();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        ListItem parent = this.currentListItem.getParent();
        if (parent == null)
            return;
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(context, parent);
        this.transportTypesList.setAdapter(menuItemAdapter);
    }

}
