package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplAuditIndexCollection extends AbstractObjectCollection 
{
    public FDCSplAuditIndexCollection()
    {
        super(FDCSplAuditIndexInfo.class);
    }
    public boolean add(FDCSplAuditIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplAuditIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplAuditIndexInfo item)
    {
        return removeObject(item);
    }
    public FDCSplAuditIndexInfo get(int index)
    {
        return(FDCSplAuditIndexInfo)getObject(index);
    }
    public FDCSplAuditIndexInfo get(Object key)
    {
        return(FDCSplAuditIndexInfo)getObject(key);
    }
    public void set(int index, FDCSplAuditIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplAuditIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplAuditIndexInfo item)
    {
        return super.indexOf(item);
    }
}