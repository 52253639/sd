package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkLoadConfirmBillEntryCollection extends AbstractObjectCollection 
{
    public WorkLoadConfirmBillEntryCollection()
    {
        super(WorkLoadConfirmBillEntryInfo.class);
    }
    public boolean add(WorkLoadConfirmBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkLoadConfirmBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkLoadConfirmBillEntryInfo item)
    {
        return removeObject(item);
    }
    public WorkLoadConfirmBillEntryInfo get(int index)
    {
        return(WorkLoadConfirmBillEntryInfo)getObject(index);
    }
    public WorkLoadConfirmBillEntryInfo get(Object key)
    {
        return(WorkLoadConfirmBillEntryInfo)getObject(key);
    }
    public void set(int index, WorkLoadConfirmBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkLoadConfirmBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkLoadConfirmBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}