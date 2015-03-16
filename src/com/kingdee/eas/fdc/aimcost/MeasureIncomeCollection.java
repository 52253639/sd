package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureIncomeCollection extends AbstractObjectCollection 
{
    public MeasureIncomeCollection()
    {
        super(MeasureIncomeInfo.class);
    }
    public boolean add(MeasureIncomeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureIncomeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureIncomeInfo item)
    {
        return removeObject(item);
    }
    public MeasureIncomeInfo get(int index)
    {
        return(MeasureIncomeInfo)getObject(index);
    }
    public MeasureIncomeInfo get(Object key)
    {
        return(MeasureIncomeInfo)getObject(key);
    }
    public void set(int index, MeasureIncomeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureIncomeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureIncomeInfo item)
    {
        return super.indexOf(item);
    }
}