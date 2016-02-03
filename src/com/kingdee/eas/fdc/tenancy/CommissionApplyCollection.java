package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommissionApplyCollection extends AbstractObjectCollection 
{
    public CommissionApplyCollection()
    {
        super(CommissionApplyInfo.class);
    }
    public boolean add(CommissionApplyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommissionApplyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommissionApplyInfo item)
    {
        return removeObject(item);
    }
    public CommissionApplyInfo get(int index)
    {
        return(CommissionApplyInfo)getObject(index);
    }
    public CommissionApplyInfo get(Object key)
    {
        return(CommissionApplyInfo)getObject(key);
    }
    public void set(int index, CommissionApplyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommissionApplyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommissionApplyInfo item)
    {
        return super.indexOf(item);
    }
}