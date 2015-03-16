package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanEntryCollection extends AbstractObjectCollection 
{
    public PlanEntryCollection()
    {
        super(PlanEntryInfo.class);
    }
    public boolean add(PlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanEntryInfo item)
    {
        return removeObject(item);
    }
    public PlanEntryInfo get(int index)
    {
        return(PlanEntryInfo)getObject(index);
    }
    public PlanEntryInfo get(Object key)
    {
        return(PlanEntryInfo)getObject(key);
    }
    public void set(int index, PlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}