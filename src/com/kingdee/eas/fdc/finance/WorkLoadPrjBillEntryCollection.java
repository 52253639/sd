package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkLoadPrjBillEntryCollection extends AbstractObjectCollection 
{
    public WorkLoadPrjBillEntryCollection()
    {
        super(WorkLoadPrjBillEntryInfo.class);
    }
    public boolean add(WorkLoadPrjBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkLoadPrjBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkLoadPrjBillEntryInfo item)
    {
        return removeObject(item);
    }
    public WorkLoadPrjBillEntryInfo get(int index)
    {
        return(WorkLoadPrjBillEntryInfo)getObject(index);
    }
    public WorkLoadPrjBillEntryInfo get(Object key)
    {
        return(WorkLoadPrjBillEntryInfo)getObject(key);
    }
    public void set(int index, WorkLoadPrjBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkLoadPrjBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkLoadPrjBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}