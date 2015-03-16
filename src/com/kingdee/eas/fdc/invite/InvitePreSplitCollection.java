package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvitePreSplitCollection extends AbstractObjectCollection 
{
    public InvitePreSplitCollection()
    {
        super(InvitePreSplitInfo.class);
    }
    public boolean add(InvitePreSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvitePreSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvitePreSplitInfo item)
    {
        return removeObject(item);
    }
    public InvitePreSplitInfo get(int index)
    {
        return(InvitePreSplitInfo)getObject(index);
    }
    public InvitePreSplitInfo get(Object key)
    {
        return(InvitePreSplitInfo)getObject(key);
    }
    public void set(int index, InvitePreSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvitePreSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvitePreSplitInfo item)
    {
        return super.indexOf(item);
    }
}