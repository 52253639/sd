package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PlanEntryE3Collection extends AbstractObjectCollection 
{
    public PlanEntryE3Collection()
    {
        super(PlanEntryE3Info.class);
    }
    public boolean add(PlanEntryE3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(PlanEntryE3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PlanEntryE3Info item)
    {
        return removeObject(item);
    }
    public PlanEntryE3Info get(int index)
    {
        return(PlanEntryE3Info)getObject(index);
    }
    public PlanEntryE3Info get(Object key)
    {
        return(PlanEntryE3Info)getObject(key);
    }
    public void set(int index, PlanEntryE3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(PlanEntryE3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PlanEntryE3Info item)
    {
        return super.indexOf(item);
    }
}