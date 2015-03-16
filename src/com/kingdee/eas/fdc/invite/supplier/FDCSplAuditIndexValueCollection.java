package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplAuditIndexValueCollection extends AbstractObjectCollection 
{
    public FDCSplAuditIndexValueCollection()
    {
        super(FDCSplAuditIndexValueInfo.class);
    }
    public boolean add(FDCSplAuditIndexValueInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplAuditIndexValueCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplAuditIndexValueInfo item)
    {
        return removeObject(item);
    }
    public FDCSplAuditIndexValueInfo get(int index)
    {
        return(FDCSplAuditIndexValueInfo)getObject(index);
    }
    public FDCSplAuditIndexValueInfo get(Object key)
    {
        return(FDCSplAuditIndexValueInfo)getObject(key);
    }
    public void set(int index, FDCSplAuditIndexValueInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplAuditIndexValueInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplAuditIndexValueInfo item)
    {
        return super.indexOf(item);
    }
}