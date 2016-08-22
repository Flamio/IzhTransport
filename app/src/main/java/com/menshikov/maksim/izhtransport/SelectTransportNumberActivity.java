package com.menshikov.maksim.izhtransport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class SelectTransportNumberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_number_transport);

        Intent intent = getIntent();
        int transportType = intent.getIntExtra("TRANSPORT_TYPE",0);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<ListItem> transportNumbers = databaseAccess.getTransportNumbers(transportType);

        ListView transportTypesList = (ListView) findViewById(R.id.transport_number_list);
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this,transportNumbers);

        transportTypesList.setAdapter(menuItemAdapter);

    }



}
