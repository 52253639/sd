package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthPlanEntryCollection extends AbstractObjectCollection 
{
    public MonthPlanEntryCollection()
    {
        super(MonthPlanEntryInfo.class);
    }
    public boolean add(MonthPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public MonthPlanEntryInfo get(int index)
    {
        return(MonthPlanEntryInfo)getObject(index);
    }
    public MonthPlanEntryInfo get(Object key)
    {
        return(MonthPlanEntryInfo)getObject(key);
    }
    public void set(int index, MonthPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}