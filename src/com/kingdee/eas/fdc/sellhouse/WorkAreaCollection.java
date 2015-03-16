package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkAreaCollection extends AbstractObjectCollection 
{
    public WorkAreaCollection()
    {
        super(WorkAreaInfo.class);
    }
    public boolean add(WorkAreaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkAreaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkAreaInfo item)
    {
        return removeObject(item);
    }
    public WorkAreaInfo get(int index)
    {
        return(WorkAreaInfo)getObject(index);
    }
    public WorkAreaInfo get(Object key)
    {
        return(WorkAreaInfo)getObject(key);
    }
    public void set(int index, WorkAreaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkAreaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkAreaInfo item)
    {
        return super.indexOf(item);
    }
}