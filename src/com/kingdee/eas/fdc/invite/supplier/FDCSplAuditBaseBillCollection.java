package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplAuditBaseBillCollection extends AbstractObjectCollection 
{
    public FDCSplAuditBaseBillCollection()
    {
        super(FDCSplAuditBaseBillInfo.class);
    }
    public boolean add(FDCSplAuditBaseBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplAuditBaseBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplAuditBaseBillInfo item)
    {
        return removeObject(item);
    }
    public FDCSplAuditBaseBillInfo get(int index)
    {
        return(FDCSplAuditBaseBillInfo)getObject(index);
    }
    public FDCSplAuditBaseBillInfo get(Object key)
    {
        return(FDCSplAuditBaseBillInfo)getObject(key);
    }
    public void set(int index, FDCSplAuditBaseBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplAuditBaseBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplAuditBaseBillInfo item)
    {
        return super.indexOf(item);
    }
}