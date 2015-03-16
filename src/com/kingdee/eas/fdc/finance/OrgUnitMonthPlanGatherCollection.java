package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgUnitMonthPlanGatherCollection extends AbstractObjectCollection 
{
    public OrgUnitMonthPlanGatherCollection()
    {
        super(OrgUnitMonthPlanGatherInfo.class);
    }
    public boolean add(OrgUnitMonthPlanGatherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgUnitMonthPlanGatherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgUnitMonthPlanGatherInfo item)
    {
        return removeObject(item);
    }
    public OrgUnitMonthPlanGatherInfo get(int index)
    {
        return(OrgUnitMonthPlanGatherInfo)getObject(index);
    }
    public OrgUnitMonthPlanGatherInfo get(Object key)
    {
        return(OrgUnitMonthPlanGatherInfo)getObject(key);
    }
    public void set(int index, OrgUnitMonthPlanGatherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgUnitMonthPlanGatherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgUnitMonthPlanGatherInfo item)
    {
        return super.indexOf(item);
    }
}