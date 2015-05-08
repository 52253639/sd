package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkAmountEntryCollection extends AbstractObjectCollection 
{
    public WorkAmountEntryCollection()
    {
        super(WorkAmountEntryInfo.class);
    }
    public boolean add(WorkAmountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkAmountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkAmountEntryInfo item)
    {
        return removeObject(item);
    }
    public WorkAmountEntryInfo get(int index)
    {
        return(WorkAmountEntryInfo)getObject(index);
    }
    public WorkAmountEntryInfo get(Object key)
    {
        return(WorkAmountEntryInfo)getObject(key);
    }
    public void set(int index, WorkAmountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkAmountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkAmountEntryInfo item)
    {
        return super.indexOf(item);
    }
}