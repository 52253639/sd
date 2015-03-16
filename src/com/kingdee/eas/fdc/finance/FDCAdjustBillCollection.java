package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCAdjustBillCollection extends AbstractObjectCollection 
{
    public FDCAdjustBillCollection()
    {
        super(FDCAdjustBillInfo.class);
    }
    public boolean add(FDCAdjustBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCAdjustBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCAdjustBillInfo item)
    {
        return removeObject(item);
    }
    public FDCAdjustBillInfo get(int index)
    {
        return(FDCAdjustBillInfo)getObject(index);
    }
    public FDCAdjustBillInfo get(Object key)
    {
        return(FDCAdjustBillInfo)getObject(key);
    }
    public void set(int index, FDCAdjustBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCAdjustBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCAdjustBillInfo item)
    {
        return super.indexOf(item);
    }
}