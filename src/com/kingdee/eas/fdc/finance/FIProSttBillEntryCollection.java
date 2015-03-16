package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FIProSttBillEntryCollection extends AbstractObjectCollection 
{
    public FIProSttBillEntryCollection()
    {
        super(FIProSttBillEntryInfo.class);
    }
    public boolean add(FIProSttBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FIProSttBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FIProSttBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FIProSttBillEntryInfo get(int index)
    {
        return(FIProSttBillEntryInfo)getObject(index);
    }
    public FIProSttBillEntryInfo get(Object key)
    {
        return(FIProSttBillEntryInfo)getObject(key);
    }
    public void set(int index, FIProSttBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FIProSttBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FIProSttBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}