package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectMonthPlanProCollection extends AbstractObjectCollection 
{
    public ProjectMonthPlanProCollection()
    {
        super(ProjectMonthPlanProInfo.class);
    }
    public boolean add(ProjectMonthPlanProInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectMonthPlanProCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectMonthPlanProInfo item)
    {
        return removeObject(item);
    }
    public ProjectMonthPlanProInfo get(int index)
    {
        return(ProjectMonthPlanProInfo)getObject(index);
    }
    public ProjectMonthPlanProInfo get(Object key)
    {
        return(ProjectMonthPlanProInfo)getObject(key);
    }
    public void set(int index, ProjectMonthPlanProInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectMonthPlanProInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectMonthPlanProInfo item)
    {
        return super.indexOf(item);
    }
}