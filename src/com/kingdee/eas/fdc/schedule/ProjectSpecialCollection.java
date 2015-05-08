package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectSpecialCollection extends AbstractObjectCollection 
{
    public ProjectSpecialCollection()
    {
        super(ProjectSpecialInfo.class);
    }
    public boolean add(ProjectSpecialInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectSpecialCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectSpecialInfo item)
    {
        return removeObject(item);
    }
    public ProjectSpecialInfo get(int index)
    {
        return(ProjectSpecialInfo)getObject(index);
    }
    public ProjectSpecialInfo get(Object key)
    {
        return(ProjectSpecialInfo)getObject(key);
    }
    public void set(int index, ProjectSpecialInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectSpecialInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectSpecialInfo item)
    {
        return super.indexOf(item);
    }
}