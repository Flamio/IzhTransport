package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.w3c.dom.ls.LSInput;

import java.util.List;


public class SelectTransportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transport);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<ListItem> transportTypes = databaseAccess.getAllTransportTypes();

        ListView transportTypesList = (ListView) findViewById(R.id.transport_types_list);

        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this,transportTypes);

        transportTypesList.setAdapter(menuItemAdapter);

        transportTypesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                ListItem selectedItem = (ListItem)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SelectTransportActivity.this,SelectTransportNumberActivity.class);
                intent.putExtra("TRANSPORT_TYPE", selectedItem.getID());
                startActivity(intent);
            }
        });
    }



}
