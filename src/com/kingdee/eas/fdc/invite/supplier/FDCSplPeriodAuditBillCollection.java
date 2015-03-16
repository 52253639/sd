package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplPeriodAuditBillCollection extends AbstractObjectCollection 
{
    public FDCSplPeriodAuditBillCollection()
    {
        super(FDCSplPeriodAuditBillInfo.class);
    }
    public boolean add(FDCSplPeriodAuditBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplPeriodAuditBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplPeriodAuditBillInfo item)
    {
        return removeObject(item);
    }
    public FDCSplPeriodAuditBillInfo get(int index)
    {
        return(FDCSplPeriodAuditBillInfo)getObject(index);
    }
    public FDCSplPeriodAuditBillInfo get(Object key)
    {
        return(FDCSplPeriodAuditBillInfo)getObject(key);
    }
    public void set(int index, FDCSplPeriodAuditBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplPeriodAuditBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplPeriodAuditBillInfo item)
    {
        return super.indexOf(item);
    }
}