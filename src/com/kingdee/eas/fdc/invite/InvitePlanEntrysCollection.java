package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvitePlanEntrysCollection extends AbstractObjectCollection 
{
    public InvitePlanEntrysCollection()
    {
        super(InvitePlanEntrysInfo.class);
    }
    public boolean add(InvitePlanEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvitePlanEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvitePlanEntrysInfo item)
    {
        return removeObject(item);
    }
    public InvitePlanEntrysInfo get(int index)
    {
        return(InvitePlanEntrysInfo)getObject(index);
    }
    public InvitePlanEntrysInfo get(Object key)
    {
        return(InvitePlanEntrysInfo)getObject(key);
    }
    public void set(int index, InvitePlanEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvitePlanEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvitePlanEntrysInfo item)
    {
        return super.indexOf(item);
    }
}