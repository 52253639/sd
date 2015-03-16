package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkAreaEntryCollection extends AbstractObjectCollection 
{
    public WorkAreaEntryCollection()
    {
        super(WorkAreaEntryInfo.class);
    }
    public boolean add(WorkAreaEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkAreaEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkAreaEntryInfo item)
    {
        return removeObject(item);
    }
    public WorkAreaEntryInfo get(int index)
    {
        return(WorkAreaEntryInfo)getObject(index);
    }
    public WorkAreaEntryInfo get(Object key)
    {
        return(WorkAreaEntryInfo)getObject(key);
    }
    public void set(int index, WorkAreaEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkAreaEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkAreaEntryInfo item)
    {
        return super.indexOf(item);
    }
}