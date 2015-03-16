package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProcurementPlanningEntryCollection extends AbstractObjectCollection 
{
    public ProcurementPlanningEntryCollection()
    {
        super(ProcurementPlanningEntryInfo.class);
    }
    public boolean add(ProcurementPlanningEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProcurementPlanningEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProcurementPlanningEntryInfo item)
    {
        return removeObject(item);
    }
    public ProcurementPlanningEntryInfo get(int index)
    {
        return(ProcurementPlanningEntryInfo)getObject(index);
    }
    public ProcurementPlanningEntryInfo get(Object key)
    {
        return(ProcurementPlanningEntryInfo)getObject(key);
    }
    public void set(int index, ProcurementPlanningEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProcurementPlanningEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProcurementPlanningEntryInfo item)
    {
        return super.indexOf(item);
    }
}