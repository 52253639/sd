package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierReviewGatherCollection extends AbstractObjectCollection 
{
    public MarketSupplierReviewGatherCollection()
    {
        super(MarketSupplierReviewGatherInfo.class);
    }
    public boolean add(MarketSupplierReviewGatherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierReviewGatherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierReviewGatherInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierReviewGatherInfo get(int index)
    {
        return(MarketSupplierReviewGatherInfo)getObject(index);
    }
    public MarketSupplierReviewGatherInfo get(Object key)
    {
        return(MarketSupplierReviewGatherInfo)getObject(key);
    }
    public void set(int index, MarketSupplierReviewGatherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierReviewGatherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierReviewGatherInfo item)
    {
        return super.indexOf(item);
    }
}