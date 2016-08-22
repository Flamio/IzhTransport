/**
 * Created by Maksim on 22.08.2016.
 */
package com.menshikov.maksim.izhtransport;

public class ListItem
{
    private int id;
    private String name;

    public ListItem(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getID()
    {
        return this.id;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
