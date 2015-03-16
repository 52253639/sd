package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteFixHeadCollection extends AbstractObjectCollection 
{
    public InviteFixHeadCollection()
    {
        super(InviteFixHeadInfo.class);
    }
    public boolean add(InviteFixHeadInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteFixHeadCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteFixHeadInfo item)
    {
        return removeObject(item);
    }
    public InviteFixHeadInfo get(int index)
    {
        return(InviteFixHeadInfo)getObject(index);
    }
    public InviteFixHeadInfo get(Object key)
    {
        return(InviteFixHeadInfo)getObject(key);
    }
    public void set(int index, InviteFixHeadInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteFixHeadInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteFixHeadInfo item)
    {
        return super.indexOf(item);
    }
}