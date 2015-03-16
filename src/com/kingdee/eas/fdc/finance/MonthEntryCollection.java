package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthEntryCollection extends AbstractObjectCollection 
{
    public MonthEntryCollection()
    {
        super(MonthEntryInfo.class);
    }
    public boolean add(MonthEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthEntryInfo item)
    {
        return removeObject(item);
    }
    public MonthEntryInfo get(int index)
    {
        return(MonthEntryInfo)getObject(index);
    }
    public MonthEntryInfo get(Object key)
    {
        return(MonthEntryInfo)getObject(key);
    }
    public void set(int index, MonthEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthEntryInfo item)
    {
        return super.indexOf(item);
    }
}