package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvitePlanCollection extends AbstractObjectCollection 
{
    public InvitePlanCollection()
    {
        super(InvitePlanInfo.class);
    }
    public boolean add(InvitePlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvitePlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvitePlanInfo item)
    {
        return removeObject(item);
    }
    public InvitePlanInfo get(int index)
    {
        return(InvitePlanInfo)getObject(index);
    }
    public InvitePlanInfo get(Object key)
    {
        return(InvitePlanInfo)getObject(key);
    }
    public void set(int index, InvitePlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvitePlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvitePlanInfo item)
    {
        return super.indexOf(item);
    }
}