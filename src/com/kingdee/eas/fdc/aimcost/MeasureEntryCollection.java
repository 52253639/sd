package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureEntryCollection extends AbstractObjectCollection 
{
    public MeasureEntryCollection()
    {
        super(MeasureEntryInfo.class);
    }
    public boolean add(MeasureEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureEntryInfo item)
    {
        return removeObject(item);
    }
    public MeasureEntryInfo get(int index)
    {
        return(MeasureEntryInfo)getObject(index);
    }
    public MeasureEntryInfo get(Object key)
    {
        return(MeasureEntryInfo)getObject(key);
    }
    public void set(int index, MeasureEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureEntryInfo item)
    {
        return super.indexOf(item);
    }
}