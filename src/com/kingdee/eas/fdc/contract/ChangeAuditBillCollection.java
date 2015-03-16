package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeAuditBillCollection extends AbstractObjectCollection 
{
    public ChangeAuditBillCollection()
    {
        super(ChangeAuditBillInfo.class);
    }
    public boolean add(ChangeAuditBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeAuditBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeAuditBillInfo item)
    {
        return removeObject(item);
    }
    public ChangeAuditBillInfo get(int index)
    {
        return(ChangeAuditBillInfo)getObject(index);
    }
    public ChangeAuditBillInfo get(Object key)
    {
        return(ChangeAuditBillInfo)getObject(key);
    }
    public void set(int index, ChangeAuditBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeAuditBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeAuditBillInfo item)
    {
        return super.indexOf(item);
    }
}