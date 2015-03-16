package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgUnitMonthPlanGatherDateEntryCollection extends AbstractObjectCollection 
{
    public OrgUnitMonthPlanGatherDateEntryCollection()
    {
        super(OrgUnitMonthPlanGatherDateEntryInfo.class);
    }
    public boolean add(OrgUnitMonthPlanGatherDateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgUnitMonthPlanGatherDateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgUnitMonthPlanGatherDateEntryInfo item)
    {
        return removeObject(item);
    }
    public OrgUnitMonthPlanGatherDateEntryInfo get(int index)
    {
        return(OrgUnitMonthPlanGatherDateEntryInfo)getObject(index);
    }
    public OrgUnitMonthPlanGatherDateEntryInfo get(Object key)
    {
        return(OrgUnitMonthPlanGatherDateEntryInfo)getObject(key);
    }
    public void set(int index, OrgUnitMonthPlanGatherDateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgUnitMonthPlanGatherDateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgUnitMonthPlanGatherDateEntryInfo item)
    {
        return super.indexOf(item);
    }
}