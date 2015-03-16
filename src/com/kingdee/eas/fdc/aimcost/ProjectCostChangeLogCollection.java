package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectCostChangeLogCollection extends AbstractObjectCollection 
{
    public ProjectCostChangeLogCollection()
    {
        super(ProjectCostChangeLogInfo.class);
    }
    public boolean add(ProjectCostChangeLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectCostChangeLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectCostChangeLogInfo item)
    {
        return removeObject(item);
    }
    public ProjectCostChangeLogInfo get(int index)
    {
        return(ProjectCostChangeLogInfo)getObject(index);
    }
    public ProjectCostChangeLogInfo get(Object key)
    {
        return(ProjectCostChangeLogInfo)getObject(key);
    }
    public void set(int index, ProjectCostChangeLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectCostChangeLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectCostChangeLogInfo item)
    {
        return super.indexOf(item);
    }
}