package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WorkLoadCostVoucherEntryCollection extends AbstractObjectCollection 
{
    public WorkLoadCostVoucherEntryCollection()
    {
        super(WorkLoadCostVoucherEntryInfo.class);
    }
    public boolean add(WorkLoadCostVoucherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WorkLoadCostVoucherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WorkLoadCostVoucherEntryInfo item)
    {
        return removeObject(item);
    }
    public WorkLoadCostVoucherEntryInfo get(int index)
    {
        return(WorkLoadCostVoucherEntryInfo)getObject(index);
    }
    public WorkLoadCostVoucherEntryInfo get(Object key)
    {
        return(WorkLoadCostVoucherEntryInfo)getObject(key);
    }
    public void set(int index, WorkLoadCostVoucherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WorkLoadCostVoucherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WorkLoadCostVoucherEntryInfo item)
    {
        return super.indexOf(item);
    }
}