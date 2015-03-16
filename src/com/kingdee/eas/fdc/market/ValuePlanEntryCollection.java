package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValuePlanEntryCollection extends AbstractObjectCollection 
{
    public ValuePlanEntryCollection()
    {
        super(ValuePlanEntryInfo.class);
    }
    public boolean add(ValuePlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValuePlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValuePlanEntryInfo item)
    {
        return removeObject(item);
    }
    public ValuePlanEntryInfo get(int index)
    {
        return(ValuePlanEntryInfo)getObject(index);
    }
    public ValuePlanEntryInfo get(Object key)
    {
        return(ValuePlanEntryInfo)getObject(key);
    }
    public void set(int index, ValuePlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValuePlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValuePlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}