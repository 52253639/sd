package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkTaskCollection extends AbstractObjectCollection 
{
    public WorkTaskCollection()
    {
        super(WorkTaskInfo.class);
    }
    public boolean add(WorkTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkTaskInfo item)
    {
        return removeObject(item);
    }
    public WorkTaskInfo get(int index)
    {
        return(WorkTaskInfo)getObject(index);
    }
    public WorkTaskInfo get(Object key)
    {
        return(WorkTaskInfo)getObject(key);
    }
    public void set(int index, WorkTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkTaskInfo item)
    {
        return super.indexOf(item);
    }
}