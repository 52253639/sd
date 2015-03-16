package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomPlanIndexEntryCollection extends AbstractObjectCollection 
{
    public CustomPlanIndexEntryCollection()
    {
        super(CustomPlanIndexEntryInfo.class);
    }
    public boolean add(CustomPlanIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomPlanIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomPlanIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public CustomPlanIndexEntryInfo get(int index)
    {
        return(CustomPlanIndexEntryInfo)getObject(index);
    }
    public CustomPlanIndexEntryInfo get(Object key)
    {
        return(CustomPlanIndexEntryInfo)getObject(key);
    }
    public void set(int index, CustomPlanIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomPlanIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomPlanIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}