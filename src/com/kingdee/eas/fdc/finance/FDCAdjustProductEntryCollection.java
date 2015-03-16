package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCAdjustProductEntryCollection extends AbstractObjectCollection 
{
    public FDCAdjustProductEntryCollection()
    {
        super(FDCAdjustProductEntryInfo.class);
    }
    public boolean add(FDCAdjustProductEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCAdjustProductEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCAdjustProductEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCAdjustProductEntryInfo get(int index)
    {
        return(FDCAdjustProductEntryInfo)getObject(index);
    }
    public FDCAdjustProductEntryInfo get(Object key)
    {
        return(FDCAdjustProductEntryInfo)getObject(key);
    }
    public void set(int index, FDCAdjustProductEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCAdjustProductEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCAdjustProductEntryInfo item)
    {
        return super.indexOf(item);
    }
}