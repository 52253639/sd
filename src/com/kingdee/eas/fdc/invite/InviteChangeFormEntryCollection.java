package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteChangeFormEntryCollection extends AbstractObjectCollection 
{
    public InviteChangeFormEntryCollection()
    {
        super(InviteChangeFormEntryInfo.class);
    }
    public boolean add(InviteChangeFormEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteChangeFormEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteChangeFormEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteChangeFormEntryInfo get(int index)
    {
        return(InviteChangeFormEntryInfo)getObject(index);
    }
    public InviteChangeFormEntryInfo get(Object key)
    {
        return(InviteChangeFormEntryInfo)getObject(key);
    }
    public void set(int index, InviteChangeFormEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteChangeFormEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteChangeFormEntryInfo item)
    {
        return super.indexOf(item);
    }
}