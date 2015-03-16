package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BillAdjustCollection extends AbstractObjectCollection 
{
    public BillAdjustCollection()
    {
        super(BillAdjustInfo.class);
    }
    public boolean add(BillAdjustInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BillAdjustCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BillAdjustInfo item)
    {
        return removeObject(item);
    }
    public BillAdjustInfo get(int index)
    {
        return(BillAdjustInfo)getObject(index);
    }
    public BillAdjustInfo get(Object key)
    {
        return(BillAdjustInfo)getObject(key);
    }
    public void set(int index, BillAdjustInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BillAdjustInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BillAdjustInfo item)
    {
        return super.indexOf(item);
    }
}