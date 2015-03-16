package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayInitDataPtyCollection extends AbstractObjectCollection 
{
    public DayInitDataPtyCollection()
    {
        super(DayInitDataPtyInfo.class);
    }
    public boolean add(DayInitDataPtyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayInitDataPtyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayInitDataPtyInfo item)
    {
        return removeObject(item);
    }
    public DayInitDataPtyInfo get(int index)
    {
        return(DayInitDataPtyInfo)getObject(index);
    }
    public DayInitDataPtyInfo get(Object key)
    {
        return(DayInitDataPtyInfo)getObject(key);
    }
    public void set(int index, DayInitDataPtyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayInitDataPtyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayInitDataPtyInfo item)
    {
        return super.indexOf(item);
    }
}