package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplAuditIndexGroupCollection extends AbstractObjectCollection 
{
    public FDCSplAuditIndexGroupCollection()
    {
        super(FDCSplAuditIndexGroupInfo.class);
    }
    public boolean add(FDCSplAuditIndexGroupInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplAuditIndexGroupCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplAuditIndexGroupInfo item)
    {
        return removeObject(item);
    }
    public FDCSplAuditIndexGroupInfo get(int index)
    {
        return(FDCSplAuditIndexGroupInfo)getObject(index);
    }
    public FDCSplAuditIndexGroupInfo get(Object key)
    {
        return(FDCSplAuditIndexGroupInfo)getObject(key);
    }
    public void set(int index, FDCSplAuditIndexGroupInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplAuditIndexGroupInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplAuditIndexGroupInfo item)
    {
        return super.indexOf(item);
    }
}