package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuyHouseReasonCollection extends AbstractObjectCollection 
{
    public BuyHouseReasonCollection()
    {
        super(BuyHouseReasonInfo.class);
    }
    public boolean add(BuyHouseReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuyHouseReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuyHouseReasonInfo item)
    {
        return removeObject(item);
    }
    public BuyHouseReasonInfo get(int index)
    {
        return(BuyHouseReasonInfo)getObject(index);
    }
    public BuyHouseReasonInfo get(Object key)
    {
        return(BuyHouseReasonInfo)getObject(key);
    }
    public void set(int index, BuyHouseReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuyHouseReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuyHouseReasonInfo item)
    {
        return super.indexOf(item);
    }
}