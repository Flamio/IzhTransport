/**
 * Created by Maksim on 22.08.2016.
 */
package com.menshikov.maksim.izhtransport.ListItem;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


abstract public class ListItem
{
    private int idItemm;
    private String name;
    private ListItem parent;
    protected static Action1<ListItem> leafAction;
    protected static Action1<ListItem> nodeAction;
    protected  List<ListItem> childrens = new ArrayList<>();

    public ListItem(int id, String name)
    {
        this.idItemm = id;
        this.name = name;
    }

    public List<ListItem> getChildrens()
    {
        return this.childrens;
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

    abstract public void operation();

    public ListItem getParent()
    {
        return parent;
    }

    protected void setParent(ListItem parent)
    {
        this.parent = parent;
    }

    public static void setLeafAction(Action1<ListItem> leafActionArg)
    {
        leafAction = leafActionArg;
    }

    public static void setNodeAction(Action1<ListItem> nodeActionArg)
    {
        nodeAction = nodeActionArg;
    }
}
