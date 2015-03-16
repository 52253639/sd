package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SincerityPurchaseCollection extends AbstractObjectCollection 
{
    public SincerityPurchaseCollection()
    {
        super(SincerityPurchaseInfo.class);
    }
    public boolean add(SincerityPurchaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SincerityPurchaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SincerityPurchaseInfo item)
    {
        return removeObject(item);
    }
    public SincerityPurchaseInfo get(int index)
    {
        return(SincerityPurchaseInfo)getObject(index);
    }
    public SincerityPurchaseInfo get(Object key)
    {
        return(SincerityPurchaseInfo)getObject(key);
    }
    public void set(int index, SincerityPurchaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SincerityPurchaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SincerityPurchaseInfo item)
    {
        return super.indexOf(item);
    }
}