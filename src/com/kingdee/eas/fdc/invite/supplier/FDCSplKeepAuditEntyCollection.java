package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplKeepAuditEntyCollection extends AbstractObjectCollection 
{
    public FDCSplKeepAuditEntyCollection()
    {
        super(FDCSplKeepAuditEntyInfo.class);
    }
    public boolean add(FDCSplKeepAuditEntyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplKeepAuditEntyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplKeepAuditEntyInfo item)
    {
        return removeObject(item);
    }
    public FDCSplKeepAuditEntyInfo get(int index)
    {
        return(FDCSplKeepAuditEntyInfo)getObject(index);
    }
    public FDCSplKeepAuditEntyInfo get(Object key)
    {
        return(FDCSplKeepAuditEntyInfo)getObject(key);
    }
    public void set(int index, FDCSplKeepAuditEntyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplKeepAuditEntyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplKeepAuditEntyInfo item)
    {
        return super.indexOf(item);
    }
}