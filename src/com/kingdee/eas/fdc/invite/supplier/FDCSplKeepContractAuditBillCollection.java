package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplKeepContractAuditBillCollection extends AbstractObjectCollection 
{
    public FDCSplKeepContractAuditBillCollection()
    {
        super(FDCSplKeepContractAuditBillInfo.class);
    }
    public boolean add(FDCSplKeepContractAuditBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplKeepContractAuditBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplKeepContractAuditBillInfo item)
    {
        return removeObject(item);
    }
    public FDCSplKeepContractAuditBillInfo get(int index)
    {
        return(FDCSplKeepContractAuditBillInfo)getObject(index);
    }
    public FDCSplKeepContractAuditBillInfo get(Object key)
    {
        return(FDCSplKeepContractAuditBillInfo)getObject(key);
    }
    public void set(int index, FDCSplKeepContractAuditBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplKeepContractAuditBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplKeepContractAuditBillInfo item)
    {
        return super.indexOf(item);
    }
}