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


public class SelectTransportActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transport);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        final ListItem transportTypes = databaseAccess.getAllTransportTypes();

        final ListView transportTypesList = (ListView) findViewById(R.id.transport_types_list);

        final Context context = this;

        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(context, transportTypes);

        transportTypesList.setAdapter(menuItemAdapter);

        final ArrayList<Integer> userTransportTypes = new ArrayList<>();

        transportTypesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                ListItem selectedItem = (ListItem) adapterView.getItemAtPosition(i);
                if (selectedItem.operation().size() > 1)
                {
                    userTransportTypes.add(selectedItem.getIdItemm());
                    MenuItemAdapter menuItemAdapter = new MenuItemAdapter(context, selectedItem);
                    transportTypesList.setAdapter(null);
                    transportTypesList.setAdapter(menuItemAdapter);
                    return;
                }

                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                intent.putExtra("TRANSPORT_TYPE", userTransportTypes.get(userTransportTypes.size() - 1));
                intent.putExtra("TRANSPORT_NUMBER", selectedItem.getName());
                userTransportTypes.clear();
                startActivity(intent);
            }
        });
    }

}
