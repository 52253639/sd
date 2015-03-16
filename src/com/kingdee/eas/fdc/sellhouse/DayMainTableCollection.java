package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayMainTableCollection extends AbstractObjectCollection 
{
    public DayMainTableCollection()
    {
        super(DayMainTableInfo.class);
    }
    public boolean add(DayMainTableInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayMainTableCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayMainTableInfo item)
    {
        return removeObject(item);
    }
    public DayMainTableInfo get(int index)
    {
        return(DayMainTableInfo)getObject(index);
    }
    public DayMainTableInfo get(Object key)
    {
        return(DayMainTableInfo)getObject(key);
    }
    public void set(int index, DayMainTableInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayMainTableInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayMainTableInfo item)
    {
        return super.indexOf(item);
    }
}