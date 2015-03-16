package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteFixCollection extends AbstractObjectCollection 
{
    public InviteFixCollection()
    {
        super(InviteFixInfo.class);
    }
    public boolean add(InviteFixInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteFixCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteFixInfo item)
    {
        return removeObject(item);
    }
    public InviteFixInfo get(int index)
    {
        return(InviteFixInfo)getObject(index);
    }
    public InviteFixInfo get(Object key)
    {
        return(InviteFixInfo)getObject(key);
    }
    public void set(int index, InviteFixInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteFixInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteFixInfo item)
    {
        return super.indexOf(item);
    }
}