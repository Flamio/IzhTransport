package com.menshikov.maksim.izhtransport.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 28.08.2016.
 */
public class NodeItem extends ListItem
{
    private List<ListItem> childrens = new ArrayList<>();

    public NodeItem(int id, String name)
    {
        super(id, name);
    }

    @Override
    public List<ListItem> operation()
    {
        return this.childrens;
    }

    public void addChildren(ListItem item)
    {
        this.childrens.add(item);
    }

}
