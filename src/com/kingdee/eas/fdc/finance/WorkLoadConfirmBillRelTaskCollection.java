package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkLoadConfirmBillRelTaskCollection extends AbstractObjectCollection 
{
    public WorkLoadConfirmBillRelTaskCollection()
    {
        super(WorkLoadConfirmBillRelTaskInfo.class);
    }
    public boolean add(WorkLoadConfirmBillRelTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkLoadConfirmBillRelTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkLoadConfirmBillRelTaskInfo item)
    {
        return removeObject(item);
    }
    public WorkLoadConfirmBillRelTaskInfo get(int index)
    {
        return(WorkLoadConfirmBillRelTaskInfo)getObject(index);
    }
    public WorkLoadConfirmBillRelTaskInfo get(Object key)
    {
        return(WorkLoadConfirmBillRelTaskInfo)getObject(key);
    }
    public void set(int index, WorkLoadConfirmBillRelTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkLoadConfirmBillRelTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkLoadConfirmBillRelTaskInfo item)
    {
        return super.indexOf(item);
    }
}