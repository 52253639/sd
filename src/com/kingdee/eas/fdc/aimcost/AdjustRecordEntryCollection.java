package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AdjustRecordEntryCollection extends AbstractObjectCollection 
{
    public AdjustRecordEntryCollection()
    {
        super(AdjustRecordEntryInfo.class);
    }
    public boolean add(AdjustRecordEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AdjustRecordEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AdjustRecordEntryInfo item)
    {
        return removeObject(item);
    }
    public AdjustRecordEntryInfo get(int index)
    {
        return(AdjustRecordEntryInfo)getObject(index);
    }
    public AdjustRecordEntryInfo get(Object key)
    {
        return(AdjustRecordEntryInfo)getObject(key);
    }
    public void set(int index, AdjustRecordEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AdjustRecordEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AdjustRecordEntryInfo item)
    {
        return super.indexOf(item);
    }
}