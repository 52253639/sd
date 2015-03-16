package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrePurchaseManageCollection extends AbstractObjectCollection 
{
    public PrePurchaseManageCollection()
    {
        super(PrePurchaseManageInfo.class);
    }
    public boolean add(PrePurchaseManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrePurchaseManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrePurchaseManageInfo item)
    {
        return removeObject(item);
    }
    public PrePurchaseManageInfo get(int index)
    {
        return(PrePurchaseManageInfo)getObject(index);
    }
    public PrePurchaseManageInfo get(Object key)
    {
        return(PrePurchaseManageInfo)getObject(key);
    }
    public void set(int index, PrePurchaseManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrePurchaseManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrePurchaseManageInfo item)
    {
        return super.indexOf(item);
    }
}