package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectInvestPlanCollection extends AbstractObjectCollection 
{
    public ProjectInvestPlanCollection()
    {
        super(ProjectInvestPlanInfo.class);
    }
    public boolean add(ProjectInvestPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectInvestPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectInvestPlanInfo item)
    {
        return removeObject(item);
    }
    public ProjectInvestPlanInfo get(int index)
    {
        return(ProjectInvestPlanInfo)getObject(index);
    }
    public ProjectInvestPlanInfo get(Object key)
    {
        return(ProjectInvestPlanInfo)getObject(key);
    }
    public void set(int index, ProjectInvestPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectInvestPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectInvestPlanInfo item)
    {
        return super.indexOf(item);
    }
}