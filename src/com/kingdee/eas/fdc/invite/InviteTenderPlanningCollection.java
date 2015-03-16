package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteTenderPlanningCollection extends AbstractObjectCollection 
{
    public InviteTenderPlanningCollection()
    {
        super(InviteTenderPlanningInfo.class);
    }
    public boolean add(InviteTenderPlanningInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteTenderPlanningCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteTenderPlanningInfo item)
    {
        return removeObject(item);
    }
    public InviteTenderPlanningInfo get(int index)
    {
        return(InviteTenderPlanningInfo)getObject(index);
    }
    public InviteTenderPlanningInfo get(Object key)
    {
        return(InviteTenderPlanningInfo)getObject(key);
    }
    public void set(int index, InviteTenderPlanningInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteTenderPlanningInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteTenderPlanningInfo item)
    {
        return super.indexOf(item);
    }
}