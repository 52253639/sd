package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplPeriodAuditBillEntryCollection extends AbstractObjectCollection 
{
    public FDCSplPeriodAuditBillEntryCollection()
    {
        super(FDCSplPeriodAuditBillEntryInfo.class);
    }
    public boolean add(FDCSplPeriodAuditBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplPeriodAuditBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplPeriodAuditBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCSplPeriodAuditBillEntryInfo get(int index)
    {
        return(FDCSplPeriodAuditBillEntryInfo)getObject(index);
    }
    public FDCSplPeriodAuditBillEntryInfo get(Object key)
    {
        return(FDCSplPeriodAuditBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCSplPeriodAuditBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplPeriodAuditBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplPeriodAuditBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}