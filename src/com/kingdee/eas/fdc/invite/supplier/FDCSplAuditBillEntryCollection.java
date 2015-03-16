package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplAuditBillEntryCollection extends AbstractObjectCollection 
{
    public FDCSplAuditBillEntryCollection()
    {
        super(FDCSplAuditBillEntryInfo.class);
    }
    public boolean add(FDCSplAuditBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplAuditBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplAuditBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCSplAuditBillEntryInfo get(int index)
    {
        return(FDCSplAuditBillEntryInfo)getObject(index);
    }
    public FDCSplAuditBillEntryInfo get(Object key)
    {
        return(FDCSplAuditBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCSplAuditBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplAuditBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplAuditBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}