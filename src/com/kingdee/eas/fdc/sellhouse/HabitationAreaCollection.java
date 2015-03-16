package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HabitationAreaCollection extends AbstractObjectCollection 
{
    public HabitationAreaCollection()
    {
        super(HabitationAreaInfo.class);
    }
    public boolean add(HabitationAreaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HabitationAreaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HabitationAreaInfo item)
    {
        return removeObject(item);
    }
    public HabitationAreaInfo get(int index)
    {
        return(HabitationAreaInfo)getObject(index);
    }
    public HabitationAreaInfo get(Object key)
    {
        return(HabitationAreaInfo)getObject(key);
    }
    public void set(int index, HabitationAreaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HabitationAreaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HabitationAreaInfo item)
    {
        return super.indexOf(item);
    }
}