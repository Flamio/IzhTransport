/**
 * Created by Maksim on 22.08.2016.
 */
package com.menshikov.maksim.izhtransport.ListItem;

import java.util.List;

abstract public class ListItem
{
    private int idItemm;
    private String name;

    public ListItem(int id, String name)
    {
        this.idItemm = id;
        this.name = name;
    }

    public void setIdItemm(int idItemm)
    {
        this.idItemm = idItemm;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public int getIdItemm()
    {
        return idItemm;
    }

    abstract public List<ListItem> operation();
}
