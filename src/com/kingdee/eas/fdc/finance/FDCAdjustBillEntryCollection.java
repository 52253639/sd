package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCAdjustBillEntryCollection extends AbstractObjectCollection 
{
    public FDCAdjustBillEntryCollection()
    {
        super(FDCAdjustBillEntryInfo.class);
    }
    public boolean add(FDCAdjustBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCAdjustBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCAdjustBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCAdjustBillEntryInfo get(int index)
    {
        return(FDCAdjustBillEntryInfo)getObject(index);
    }
    public FDCAdjustBillEntryInfo get(Object key)
    {
        return(FDCAdjustBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCAdjustBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCAdjustBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCAdjustBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}