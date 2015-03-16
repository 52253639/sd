package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkLoadPayVoucherEntryCollection extends AbstractObjectCollection 
{
    public WorkLoadPayVoucherEntryCollection()
    {
        super(WorkLoadPayVoucherEntryInfo.class);
    }
    public boolean add(WorkLoadPayVoucherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkLoadPayVoucherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkLoadPayVoucherEntryInfo item)
    {
        return removeObject(item);
    }
    public WorkLoadPayVoucherEntryInfo get(int index)
    {
        return(WorkLoadPayVoucherEntryInfo)getObject(index);
    }
    public WorkLoadPayVoucherEntryInfo get(Object key)
    {
        return(WorkLoadPayVoucherEntryInfo)getObject(key);
    }
    public void set(int index, WorkLoadPayVoucherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkLoadPayVoucherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkLoadPayVoucherEntryInfo item)
    {
        return super.indexOf(item);
    }
}