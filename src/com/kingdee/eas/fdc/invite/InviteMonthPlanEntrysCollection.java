package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteMonthPlanEntrysCollection extends AbstractObjectCollection 
{
    public InviteMonthPlanEntrysCollection()
    {
        super(InviteMonthPlanEntrysInfo.class);
    }
    public boolean add(InviteMonthPlanEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteMonthPlanEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteMonthPlanEntrysInfo item)
    {
        return removeObject(item);
    }
    public InviteMonthPlanEntrysInfo get(int index)
    {
        return(InviteMonthPlanEntrysInfo)getObject(index);
    }
    public InviteMonthPlanEntrysInfo get(Object key)
    {
        return(InviteMonthPlanEntrysInfo)getObject(key);
    }
    public void set(int index, InviteMonthPlanEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteMonthPlanEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteMonthPlanEntrysInfo item)
    {
        return super.indexOf(item);
    }
}