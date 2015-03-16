package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeReasonCollection extends AbstractObjectCollection 
{
    public PurchaseChangeReasonCollection()
    {
        super(PurchaseChangeReasonInfo.class);
    }
    public boolean add(PurchaseChangeReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeReasonInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeReasonInfo get(int index)
    {
        return(PurchaseChangeReasonInfo)getObject(index);
    }
    public PurchaseChangeReasonInfo get(Object key)
    {
        return(PurchaseChangeReasonInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeReasonInfo item)
    {
        return super.indexOf(item);
    }
}