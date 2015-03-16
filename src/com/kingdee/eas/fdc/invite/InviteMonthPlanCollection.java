package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteMonthPlanCollection extends AbstractObjectCollection 
{
    public InviteMonthPlanCollection()
    {
        super(InviteMonthPlanInfo.class);
    }
    public boolean add(InviteMonthPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteMonthPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteMonthPlanInfo item)
    {
        return removeObject(item);
    }
    public InviteMonthPlanInfo get(int index)
    {
        return(InviteMonthPlanInfo)getObject(index);
    }
    public InviteMonthPlanInfo get(Object key)
    {
        return(InviteMonthPlanInfo)getObject(key);
    }
    public void set(int index, InviteMonthPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteMonthPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteMonthPlanInfo item)
    {
        return super.indexOf(item);
    }
}