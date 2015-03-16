package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkLoadConfirmBillCollection extends AbstractObjectCollection 
{
    public WorkLoadConfirmBillCollection()
    {
        super(WorkLoadConfirmBillInfo.class);
    }
    public boolean add(WorkLoadConfirmBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkLoadConfirmBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkLoadConfirmBillInfo item)
    {
        return removeObject(item);
    }
    public WorkLoadConfirmBillInfo get(int index)
    {
        return(WorkLoadConfirmBillInfo)getObject(index);
    }
    public WorkLoadConfirmBillInfo get(Object key)
    {
        return(WorkLoadConfirmBillInfo)getObject(key);
    }
    public void set(int index, WorkLoadConfirmBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkLoadConfirmBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkLoadConfirmBillInfo item)
    {
        return super.indexOf(item);
    }
}