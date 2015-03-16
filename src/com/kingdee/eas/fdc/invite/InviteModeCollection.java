package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteModeCollection extends AbstractObjectCollection 
{
    public InviteModeCollection()
    {
        super(InviteModeInfo.class);
    }
    public boolean add(InviteModeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteModeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteModeInfo item)
    {
        return removeObject(item);
    }
    public InviteModeInfo get(int index)
    {
        return(InviteModeInfo)getObject(index);
    }
    public InviteModeInfo get(Object key)
    {
        return(InviteModeInfo)getObject(key);
    }
    public void set(int index, InviteModeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteModeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteModeInfo item)
    {
        return super.indexOf(item);
    }
}