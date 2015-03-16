package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PolicyManageCollection extends AbstractObjectCollection 
{
    public PolicyManageCollection()
    {
        super(PolicyManageInfo.class);
    }
    public boolean add(PolicyManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PolicyManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PolicyManageInfo item)
    {
        return removeObject(item);
    }
    public PolicyManageInfo get(int index)
    {
        return(PolicyManageInfo)getObject(index);
    }
    public PolicyManageInfo get(Object key)
    {
        return(PolicyManageInfo)getObject(key);
    }
    public void set(int index, PolicyManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PolicyManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PolicyManageInfo item)
    {
        return super.indexOf(item);
    }
}