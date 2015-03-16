package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteTenderPlanningEntryCollection extends AbstractObjectCollection 
{
    public InviteTenderPlanningEntryCollection()
    {
        super(InviteTenderPlanningEntryInfo.class);
    }
    public boolean add(InviteTenderPlanningEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteTenderPlanningEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteTenderPlanningEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteTenderPlanningEntryInfo get(int index)
    {
        return(InviteTenderPlanningEntryInfo)getObject(index);
    }
    public InviteTenderPlanningEntryInfo get(Object key)
    {
        return(InviteTenderPlanningEntryInfo)getObject(key);
    }
    public void set(int index, InviteTenderPlanningEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteTenderPlanningEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteTenderPlanningEntryInfo item)
    {
        return super.indexOf(item);
    }
}