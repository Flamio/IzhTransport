package com.menshikov.maksim.izhtransport.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksim on 28.08.2016.
 */
public class NodeItem extends ListItem
{
    public NodeItem(int id, String name)
    {
        super(id, name);
    }

    @Override
    public void operation()
    {
        if (nodeAction == null)
            return;

        nodeAction.call(this);
    }

    public void addChildren(ListItem item)
    {
        item.setParent(this);
        this.childrens.add(item);
    }

}
