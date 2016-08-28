package com.menshikov.maksim.izhtransport.ListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maksim on 28.08.2016.
 */
public class ListLeaf extends ListItem
{
    public ListLeaf(int id, String name)
    {
        super(id, name);
    }

    @Override
    public List<ListItem> operation()
    {
        return Arrays.asList((ListItem)this);
    }
}
