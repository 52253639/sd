package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PolicyManageEntryCollection extends AbstractObjectCollection 
{
    public PolicyManageEntryCollection()
    {
        super(PolicyManageEntryInfo.class);
    }
    public boolean add(PolicyManageEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PolicyManageEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PolicyManageEntryInfo item)
    {
        return removeObject(item);
    }
    public PolicyManageEntryInfo get(int index)
    {
        return(PolicyManageEntryInfo)getObject(index);
    }
    public PolicyManageEntryInfo get(Object key)
    {
        return(PolicyManageEntryInfo)getObject(key);
    }
    public void set(int index, PolicyManageEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PolicyManageEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PolicyManageEntryInfo item)
    {
        return super.indexOf(item);
    }
}