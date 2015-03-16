package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteProjectEntryCollection extends AbstractObjectCollection 
{
    public InviteProjectEntryCollection()
    {
        super(InviteProjectEntryInfo.class);
    }
    public boolean add(InviteProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteProjectEntryInfo get(int index)
    {
        return(InviteProjectEntryInfo)getObject(index);
    }
    public InviteProjectEntryInfo get(Object key)
    {
        return(InviteProjectEntryInfo)getObject(key);
    }
    public void set(int index, InviteProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}