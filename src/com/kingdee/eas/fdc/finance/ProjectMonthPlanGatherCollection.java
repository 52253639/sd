package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanGatherCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanGatherCollection()
    {
        super(ProjectMonthPlanGatherInfo.class);
    }
    public boolean add(ProjectMonthPlanGatherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanGatherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanGatherInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanGatherInfo get(int index)
    {
        return(ProjectMonthPlanGatherInfo)getObject(index);
    }
    public ProjectMonthPlanGatherInfo get(Object key)
    {
        return(ProjectMonthPlanGatherInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanGatherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanGatherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanGatherInfo item)
    {
        return super.indexOf(item);
    }
}