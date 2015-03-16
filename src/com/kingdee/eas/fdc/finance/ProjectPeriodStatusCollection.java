package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectPeriodStatusCollection extends AbstractObjectCollection 
{
    public ProjectPeriodStatusCollection()
    {
        super(ProjectPeriodStatusInfo.class);
    }
    public boolean add(ProjectPeriodStatusInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectPeriodStatusCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectPeriodStatusInfo item)
    {
        return removeObject(item);
    }
    public ProjectPeriodStatusInfo get(int index)
    {
        return(ProjectPeriodStatusInfo)getObject(index);
    }
    public ProjectPeriodStatusInfo get(Object key)
    {
        return(ProjectPeriodStatusInfo)getObject(key);
    }
    public void set(int index, ProjectPeriodStatusInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectPeriodStatusInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectPeriodStatusInfo item)
    {
        return super.indexOf(item);
    }
}