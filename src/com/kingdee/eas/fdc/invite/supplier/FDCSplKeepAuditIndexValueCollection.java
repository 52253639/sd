package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplKeepAuditIndexValueCollection extends AbstractObjectCollection 
{
    public FDCSplKeepAuditIndexValueCollection()
    {
        super(FDCSplKeepAuditIndexValueInfo.class);
    }
    public boolean add(FDCSplKeepAuditIndexValueInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplKeepAuditIndexValueCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplKeepAuditIndexValueInfo item)
    {
        return removeObject(item);
    }
    public FDCSplKeepAuditIndexValueInfo get(int index)
    {
        return(FDCSplKeepAuditIndexValueInfo)getObject(index);
    }
    public FDCSplKeepAuditIndexValueInfo get(Object key)
    {
        return(FDCSplKeepAuditIndexValueInfo)getObject(key);
    }
    public void set(int index, FDCSplKeepAuditIndexValueInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplKeepAuditIndexValueInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplKeepAuditIndexValueInfo item)
    {
        return super.indexOf(item);
    }
}