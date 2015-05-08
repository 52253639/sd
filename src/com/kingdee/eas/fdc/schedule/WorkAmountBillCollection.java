package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkAmountBillCollection extends AbstractObjectCollection 
{
    public WorkAmountBillCollection()
    {
        super(WorkAmountBillInfo.class);
    }
    public boolean add(WorkAmountBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkAmountBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkAmountBillInfo item)
    {
        return removeObject(item);
    }
    public WorkAmountBillInfo get(int index)
    {
        return(WorkAmountBillInfo)getObject(index);
    }
    public WorkAmountBillInfo get(Object key)
    {
        return(WorkAmountBillInfo)getObject(key);
    }
    public void set(int index, WorkAmountBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkAmountBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkAmountBillInfo item)
    {
        return super.indexOf(item);
    }
}