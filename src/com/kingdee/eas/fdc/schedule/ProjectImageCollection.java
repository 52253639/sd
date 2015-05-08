package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectImageCollection extends AbstractObjectCollection 
{
    public ProjectImageCollection()
    {
        super(ProjectImageInfo.class);
    }
    public boolean add(ProjectImageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectImageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectImageInfo item)
    {
        return removeObject(item);
    }
    public ProjectImageInfo get(int index)
    {
        return(ProjectImageInfo)getObject(index);
    }
    public ProjectImageInfo get(Object key)
    {
        return(ProjectImageInfo)getObject(key);
    }
    public void set(int index, ProjectImageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectImageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectImageInfo item)
    {
        return super.indexOf(item);
    }
}