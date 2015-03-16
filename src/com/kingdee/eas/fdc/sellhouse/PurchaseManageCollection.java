package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseManageCollection extends AbstractObjectCollection 
{
    public PurchaseManageCollection()
    {
        super(PurchaseManageInfo.class);
    }
    public boolean add(PurchaseManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseManageInfo item)
    {
        return removeObject(item);
    }
    public PurchaseManageInfo get(int index)
    {
        return(PurchaseManageInfo)getObject(index);
    }
    public PurchaseManageInfo get(Object key)
    {
        return(PurchaseManageInfo)getObject(key);
    }
    public void set(int index, PurchaseManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseManageInfo item)
    {
        return super.indexOf(item);
    }
}