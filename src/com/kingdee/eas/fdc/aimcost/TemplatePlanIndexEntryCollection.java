package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplatePlanIndexEntryCollection extends AbstractObjectCollection 
{
    public TemplatePlanIndexEntryCollection()
    {
        super(TemplatePlanIndexEntryInfo.class);
    }
    public boolean add(TemplatePlanIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplatePlanIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplatePlanIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public TemplatePlanIndexEntryInfo get(int index)
    {
        return(TemplatePlanIndexEntryInfo)getObject(index);
    }
    public TemplatePlanIndexEntryInfo get(Object key)
    {
        return(TemplatePlanIndexEntryInfo)getObject(key);
    }
    public void set(int index, TemplatePlanIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplatePlanIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplatePlanIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}