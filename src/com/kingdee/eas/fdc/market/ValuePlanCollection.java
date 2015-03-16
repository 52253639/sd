package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValuePlanCollection extends AbstractObjectCollection 
{
    public ValuePlanCollection()
    {
        super(ValuePlanInfo.class);
    }
    public boolean add(ValuePlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValuePlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValuePlanInfo item)
    {
        return removeObject(item);
    }
    public ValuePlanInfo get(int index)
    {
        return(ValuePlanInfo)getObject(index);
    }
    public ValuePlanInfo get(Object key)
    {
        return(ValuePlanInfo)getObject(key);
    }
    public void set(int index, ValuePlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValuePlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValuePlanInfo item)
    {
        return super.indexOf(item);
    }
}