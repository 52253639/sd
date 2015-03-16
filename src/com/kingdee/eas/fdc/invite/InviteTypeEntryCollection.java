package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteTypeEntryCollection extends AbstractObjectCollection 
{
    public InviteTypeEntryCollection()
    {
        super(InviteTypeEntryInfo.class);
    }
    public boolean add(InviteTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteTypeEntryInfo get(int index)
    {
        return(InviteTypeEntryInfo)getObject(index);
    }
    public InviteTypeEntryInfo get(Object key)
    {
        return(InviteTypeEntryInfo)getObject(key);
    }
    public void set(int index, InviteTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}