package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayRecepTypeCollection extends AbstractObjectCollection 
{
    public DayRecepTypeCollection()
    {
        super(DayRecepTypeInfo.class);
    }
    public boolean add(DayRecepTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayRecepTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayRecepTypeInfo item)
    {
        return removeObject(item);
    }
    public DayRecepTypeInfo get(int index)
    {
        return(DayRecepTypeInfo)getObject(index);
    }
    public DayRecepTypeInfo get(Object key)
    {
        return(DayRecepTypeInfo)getObject(key);
    }
    public void set(int index, DayRecepTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayRecepTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayRecepTypeInfo item)
    {
        return super.indexOf(item);
    }
}