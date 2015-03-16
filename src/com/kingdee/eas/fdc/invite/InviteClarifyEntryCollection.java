package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteClarifyEntryCollection extends AbstractObjectCollection 
{
    public InviteClarifyEntryCollection()
    {
        super(InviteClarifyEntryInfo.class);
    }
    public boolean add(InviteClarifyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteClarifyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteClarifyEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteClarifyEntryInfo get(int index)
    {
        return(InviteClarifyEntryInfo)getObject(index);
    }
    public InviteClarifyEntryInfo get(Object key)
    {
        return(InviteClarifyEntryInfo)getObject(key);
    }
    public void set(int index, InviteClarifyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteClarifyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteClarifyEntryInfo item)
    {
        return super.indexOf(item);
    }
}