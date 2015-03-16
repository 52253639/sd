package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteClarifyCollection extends AbstractObjectCollection 
{
    public InviteClarifyCollection()
    {
        super(InviteClarifyInfo.class);
    }
    public boolean add(InviteClarifyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteClarifyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteClarifyInfo item)
    {
        return removeObject(item);
    }
    public InviteClarifyInfo get(int index)
    {
        return(InviteClarifyInfo)getObject(index);
    }
    public InviteClarifyInfo get(Object key)
    {
        return(InviteClarifyInfo)getObject(key);
    }
    public void set(int index, InviteClarifyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteClarifyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteClarifyInfo item)
    {
        return super.indexOf(item);
    }
}