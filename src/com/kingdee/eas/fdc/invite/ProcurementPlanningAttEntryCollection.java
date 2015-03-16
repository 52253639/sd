package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProcurementPlanningAttEntryCollection extends AbstractObjectCollection 
{
    public ProcurementPlanningAttEntryCollection()
    {
        super(ProcurementPlanningAttEntryInfo.class);
    }
    public boolean add(ProcurementPlanningAttEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProcurementPlanningAttEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProcurementPlanningAttEntryInfo item)
    {
        return removeObject(item);
    }
    public ProcurementPlanningAttEntryInfo get(int index)
    {
        return(ProcurementPlanningAttEntryInfo)getObject(index);
    }
    public ProcurementPlanningAttEntryInfo get(Object key)
    {
        return(ProcurementPlanningAttEntryInfo)getObject(key);
    }
    public void set(int index, ProcurementPlanningAttEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProcurementPlanningAttEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProcurementPlanningAttEntryInfo item)
    {
        return super.indexOf(item);
    }
}