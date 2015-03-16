package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureIncomeEntryCollection extends AbstractObjectCollection 
{
    public MeasureIncomeEntryCollection()
    {
        super(MeasureIncomeEntryInfo.class);
    }
    public boolean add(MeasureIncomeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureIncomeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureIncomeEntryInfo item)
    {
        return removeObject(item);
    }
    public MeasureIncomeEntryInfo get(int index)
    {
        return(MeasureIncomeEntryInfo)getObject(index);
    }
    public MeasureIncomeEntryInfo get(Object key)
    {
        return(MeasureIncomeEntryInfo)getObject(key);
    }
    public void set(int index, MeasureIncomeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureIncomeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureIncomeEntryInfo item)
    {
        return super.indexOf(item);
    }
}