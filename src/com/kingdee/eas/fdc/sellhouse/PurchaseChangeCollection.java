package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeCollection extends AbstractObjectCollection 
{
    public PurchaseChangeCollection()
    {
        super(PurchaseChangeInfo.class);
    }
    public boolean add(PurchaseChangeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeInfo get(int index)
    {
        return(PurchaseChangeInfo)getObject(index);
    }
    public PurchaseChangeInfo get(Object key)
    {
        return(PurchaseChangeInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeInfo item)
    {
        return super.indexOf(item);
    }
}