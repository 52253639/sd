package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteFileItemEntryCollection extends AbstractObjectCollection 
{
    public InviteFileItemEntryCollection()
    {
        super(InviteFileItemEntryInfo.class);
    }
    public boolean add(InviteFileItemEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteFileItemEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteFileItemEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteFileItemEntryInfo get(int index)
    {
        return(InviteFileItemEntryInfo)getObject(index);
    }
    public InviteFileItemEntryInfo get(Object key)
    {
        return(InviteFileItemEntryInfo)getObject(key);
    }
    public void set(int index, InviteFileItemEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteFileItemEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteFileItemEntryInfo item)
    {
        return super.indexOf(item);
    }
}