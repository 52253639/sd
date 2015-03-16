package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgUnitMonthPlanGatherEntryCollection extends AbstractObjectCollection 
{
    public OrgUnitMonthPlanGatherEntryCollection()
    {
        super(OrgUnitMonthPlanGatherEntryInfo.class);
    }
    public boolean add(OrgUnitMonthPlanGatherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgUnitMonthPlanGatherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgUnitMonthPlanGatherEntryInfo item)
    {
        return removeObject(item);
    }
    public OrgUnitMonthPlanGatherEntryInfo get(int index)
    {
        return(OrgUnitMonthPlanGatherEntryInfo)getObject(index);
    }
    public OrgUnitMonthPlanGatherEntryInfo get(Object key)
    {
        return(OrgUnitMonthPlanGatherEntryInfo)getObject(key);
    }
    public void set(int index, OrgUnitMonthPlanGatherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgUnitMonthPlanGatherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgUnitMonthPlanGatherEntryInfo item)
    {
        return super.indexOf(item);
    }
}