package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValuePlanDetialEntryCollection extends AbstractObjectCollection 
{
    public ValuePlanDetialEntryCollection()
    {
        super(ValuePlanDetialEntryInfo.class);
    }
    public boolean add(ValuePlanDetialEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValuePlanDetialEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValuePlanDetialEntryInfo item)
    {
        return removeObject(item);
    }
    public ValuePlanDetialEntryInfo get(int index)
    {
        return(ValuePlanDetialEntryInfo)getObject(index);
    }
    public ValuePlanDetialEntryInfo get(Object key)
    {
        return(ValuePlanDetialEntryInfo)getObject(key);
    }
    public void set(int index, ValuePlanDetialEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValuePlanDetialEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValuePlanDetialEntryInfo item)
    {
        return super.indexOf(item);
    }
}