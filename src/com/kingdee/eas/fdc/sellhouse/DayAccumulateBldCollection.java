package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayAccumulateBldCollection extends AbstractObjectCollection 
{
    public DayAccumulateBldCollection()
    {
        super(DayAccumulateBldInfo.class);
    }
    public boolean add(DayAccumulateBldInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayAccumulateBldCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayAccumulateBldInfo item)
    {
        return removeObject(item);
    }
    public DayAccumulateBldInfo get(int index)
    {
        return(DayAccumulateBldInfo)getObject(index);
    }
    public DayAccumulateBldInfo get(Object key)
    {
        return(DayAccumulateBldInfo)getObject(key);
    }
    public void set(int index, DayAccumulateBldInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayAccumulateBldInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayAccumulateBldInfo item)
    {
        return super.indexOf(item);
    }
}