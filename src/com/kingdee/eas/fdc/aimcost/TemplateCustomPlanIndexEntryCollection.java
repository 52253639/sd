package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateCustomPlanIndexEntryCollection extends AbstractObjectCollection 
{
    public TemplateCustomPlanIndexEntryCollection()
    {
        super(TemplateCustomPlanIndexEntryInfo.class);
    }
    public boolean add(TemplateCustomPlanIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateCustomPlanIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateCustomPlanIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public TemplateCustomPlanIndexEntryInfo get(int index)
    {
        return(TemplateCustomPlanIndexEntryInfo)getObject(index);
    }
    public TemplateCustomPlanIndexEntryInfo get(Object key)
    {
        return(TemplateCustomPlanIndexEntryInfo)getObject(key);
    }
    public void set(int index, TemplateCustomPlanIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateCustomPlanIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateCustomPlanIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}