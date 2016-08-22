package com.menshikov.maksim.izhtransport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Maksim on 22.08.2016.
 */
public class MenuItemAdapter extends ArrayAdapter<ListItem>
{


    public MenuItemAdapter(Context context, List<ListItem> items)
    {
        super(context, 0, items);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent)
    {
        ListItem menuItem = this.getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView listItemName = (TextView) convertView.findViewById(android.R.id.text1);

        listItemName.setText(menuItem.getName());

        return convertView;
    }

}
