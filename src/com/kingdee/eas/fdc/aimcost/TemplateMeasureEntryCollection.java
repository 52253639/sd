package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateMeasureEntryCollection extends AbstractObjectCollection 
{
    public TemplateMeasureEntryCollection()
    {
        super(TemplateMeasureEntryInfo.class);
    }
    public boolean add(TemplateMeasureEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateMeasureEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateMeasureEntryInfo item)
    {
        return removeObject(item);
    }
    public TemplateMeasureEntryInfo get(int index)
    {
        return(TemplateMeasureEntryInfo)getObject(index);
    }
    public TemplateMeasureEntryInfo get(Object key)
    {
        return(TemplateMeasureEntryInfo)getObject(key);
    }
    public void set(int index, TemplateMeasureEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateMeasureEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateMeasureEntryInfo item)
    {
        return super.indexOf(item);
    }
}