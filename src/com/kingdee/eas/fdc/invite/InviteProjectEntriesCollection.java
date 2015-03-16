package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteProjectEntriesCollection extends AbstractObjectCollection 
{
    public InviteProjectEntriesCollection()
    {
        super(InviteProjectEntriesInfo.class);
    }
    public boolean add(InviteProjectEntriesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteProjectEntriesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteProjectEntriesInfo item)
    {
        return removeObject(item);
    }
    public InviteProjectEntriesInfo get(int index)
    {
        return(InviteProjectEntriesInfo)getObject(index);
    }
    public InviteProjectEntriesInfo get(Object key)
    {
        return(InviteProjectEntriesInfo)getObject(key);
    }
    public void set(int index, InviteProjectEntriesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteProjectEntriesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteProjectEntriesInfo item)
    {
        return super.indexOf(item);
    }
}