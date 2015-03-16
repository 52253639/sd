package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SaleBalanceCollection extends AbstractObjectCollection 
{
    public SaleBalanceCollection()
    {
        super(SaleBalanceInfo.class);
    }
    public boolean add(SaleBalanceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SaleBalanceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SaleBalanceInfo item)
    {
        return removeObject(item);
    }
    public SaleBalanceInfo get(int index)
    {
        return(SaleBalanceInfo)getObject(index);
    }
    public SaleBalanceInfo get(Object key)
    {
        return(SaleBalanceInfo)getObject(key);
    }
    public void set(int index, SaleBalanceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SaleBalanceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SaleBalanceInfo item)
    {
        return super.indexOf(item);
    }
}