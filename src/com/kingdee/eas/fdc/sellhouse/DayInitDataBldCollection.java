package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayInitDataBldCollection extends AbstractObjectCollection 
{
    public DayInitDataBldCollection()
    {
        super(DayInitDataBldInfo.class);
    }
    public boolean add(DayInitDataBldInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayInitDataBldCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayInitDataBldInfo item)
    {
        return removeObject(item);
    }
    public DayInitDataBldInfo get(int index)
    {
        return(DayInitDataBldInfo)getObject(index);
    }
    public DayInitDataBldInfo get(Object key)
    {
        return(DayInitDataBldInfo)getObject(key);
    }
    public void set(int index, DayInitDataBldInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayInitDataBldInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayInitDataBldInfo item)
    {
        return super.indexOf(item);
    }
}