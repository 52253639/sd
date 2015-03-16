package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCReceivingBillCollection extends AbstractObjectCollection 
{
    public FDCReceivingBillCollection()
    {
        super(FDCReceivingBillInfo.class);
    }
    public boolean add(FDCReceivingBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCReceivingBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCReceivingBillInfo item)
    {
        return removeObject(item);
    }
    public FDCReceivingBillInfo get(int index)
    {
        return(FDCReceivingBillInfo)getObject(index);
    }
    public FDCReceivingBillInfo get(Object key)
    {
        return(FDCReceivingBillInfo)getObject(key);
    }
    public void set(int index, FDCReceivingBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCReceivingBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCReceivingBillInfo item)
    {
        return super.indexOf(item);
    }
}