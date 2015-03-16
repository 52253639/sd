package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCAdjustBillAllInfoCollection extends AbstractObjectCollection 
{
    public FDCAdjustBillAllInfoCollection()
    {
        super(FDCAdjustBillAllInfoInfo.class);
    }
    public boolean add(FDCAdjustBillAllInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCAdjustBillAllInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCAdjustBillAllInfoInfo item)
    {
        return removeObject(item);
    }
    public FDCAdjustBillAllInfoInfo get(int index)
    {
        return(FDCAdjustBillAllInfoInfo)getObject(index);
    }
    public FDCAdjustBillAllInfoInfo get(Object key)
    {
        return(FDCAdjustBillAllInfoInfo)getObject(key);
    }
    public void set(int index, FDCAdjustBillAllInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCAdjustBillAllInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCAdjustBillAllInfoInfo item)
    {
        return super.indexOf(item);
    }
}