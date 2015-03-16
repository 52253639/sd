package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCReceivingBillEntryCollection extends AbstractObjectCollection 
{
    public FDCReceivingBillEntryCollection()
    {
        super(FDCReceivingBillEntryInfo.class);
    }
    public boolean add(FDCReceivingBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCReceivingBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCReceivingBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCReceivingBillEntryInfo get(int index)
    {
        return(FDCReceivingBillEntryInfo)getObject(index);
    }
    public FDCReceivingBillEntryInfo get(Object key)
    {
        return(FDCReceivingBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCReceivingBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCReceivingBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCReceivingBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}