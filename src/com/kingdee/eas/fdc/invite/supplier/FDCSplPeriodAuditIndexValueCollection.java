package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplPeriodAuditIndexValueCollection extends AbstractObjectCollection 
{
    public FDCSplPeriodAuditIndexValueCollection()
    {
        super(FDCSplPeriodAuditIndexValueInfo.class);
    }
    public boolean add(FDCSplPeriodAuditIndexValueInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplPeriodAuditIndexValueCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplPeriodAuditIndexValueInfo item)
    {
        return removeObject(item);
    }
    public FDCSplPeriodAuditIndexValueInfo get(int index)
    {
        return(FDCSplPeriodAuditIndexValueInfo)getObject(index);
    }
    public FDCSplPeriodAuditIndexValueInfo get(Object key)
    {
        return(FDCSplPeriodAuditIndexValueInfo)getObject(key);
    }
    public void set(int index, FDCSplPeriodAuditIndexValueInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplPeriodAuditIndexValueInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplPeriodAuditIndexValueInfo item)
    {
        return super.indexOf(item);
    }
}