package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvitePreSplitEntryCollection extends AbstractObjectCollection 
{
    public InvitePreSplitEntryCollection()
    {
        super(InvitePreSplitEntryInfo.class);
    }
    public boolean add(InvitePreSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvitePreSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvitePreSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public InvitePreSplitEntryInfo get(int index)
    {
        return(InvitePreSplitEntryInfo)getObject(index);
    }
    public InvitePreSplitEntryInfo get(Object key)
    {
        return(InvitePreSplitEntryInfo)getObject(key);
    }
    public void set(int index, InvitePreSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvitePreSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvitePreSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}