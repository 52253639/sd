package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProcurementPlanningCollection extends AbstractObjectCollection 
{
    public ProcurementPlanningCollection()
    {
        super(ProcurementPlanningInfo.class);
    }
    public boolean add(ProcurementPlanningInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProcurementPlanningCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProcurementPlanningInfo item)
    {
        return removeObject(item);
    }
    public ProcurementPlanningInfo get(int index)
    {
        return(ProcurementPlanningInfo)getObject(index);
    }
    public ProcurementPlanningInfo get(Object key)
    {
        return(ProcurementPlanningInfo)getObject(key);
    }
    public void set(int index, ProcurementPlanningInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProcurementPlanningInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProcurementPlanningInfo item)
    {
        return super.indexOf(item);
    }
}