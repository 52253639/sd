package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanAdjustReasonCollection extends AbstractObjectCollection 
{
    public PlanAdjustReasonCollection()
    {
        super(PlanAdjustReasonInfo.class);
    }
    public boolean add(PlanAdjustReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanAdjustReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanAdjustReasonInfo item)
    {
        return removeObject(item);
    }
    public PlanAdjustReasonInfo get(int index)
    {
        return(PlanAdjustReasonInfo)getObject(index);
    }
    public PlanAdjustReasonInfo get(Object key)
    {
        return(PlanAdjustReasonInfo)getObject(key);
    }
    public void set(int index, PlanAdjustReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanAdjustReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanAdjustReasonInfo item)
    {
        return super.indexOf(item);
    }
}