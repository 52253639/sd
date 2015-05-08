package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectImageEntryCollection extends AbstractObjectCollection 
{
    public ProjectImageEntryCollection()
    {
        super(ProjectImageEntryInfo.class);
    }
    public boolean add(ProjectImageEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectImageEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectImageEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectImageEntryInfo get(int index)
    {
        return(ProjectImageEntryInfo)getObject(index);
    }
    public ProjectImageEntryInfo get(Object key)
    {
        return(ProjectImageEntryInfo)getObject(key);
    }
    public void set(int index, ProjectImageEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectImageEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectImageEntryInfo item)
    {
        return super.indexOf(item);
    }
}