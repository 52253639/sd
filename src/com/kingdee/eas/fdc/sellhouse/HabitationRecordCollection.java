package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HabitationRecordCollection extends AbstractObjectCollection 
{
    public HabitationRecordCollection()
    {
        super(HabitationRecordInfo.class);
    }
    public boolean add(HabitationRecordInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HabitationRecordCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HabitationRecordInfo item)
    {
        return removeObject(item);
    }
    public HabitationRecordInfo get(int index)
    {
        return(HabitationRecordInfo)getObject(index);
    }
    public HabitationRecordInfo get(Object key)
    {
        return(HabitationRecordInfo)getObject(key);
    }
    public void set(int index, HabitationRecordInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HabitationRecordInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HabitationRecordInfo item)
    {
        return super.indexOf(item);
    }
}