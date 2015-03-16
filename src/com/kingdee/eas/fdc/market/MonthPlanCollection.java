package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthPlanCollection extends AbstractObjectCollection 
{
    public MonthPlanCollection()
    {
        super(MonthPlanInfo.class);
    }
    public boolean add(MonthPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthPlanInfo item)
    {
        return removeObject(item);
    }
    public MonthPlanInfo get(int index)
    {
        return(MonthPlanInfo)getObject(index);
    }
    public MonthPlanInfo get(Object key)
    {
        return(MonthPlanInfo)getObject(key);
    }
    public void set(int index, MonthPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthPlanInfo item)
    {
        return super.indexOf(item);
    }
}