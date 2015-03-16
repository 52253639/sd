package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierReviewGatherPersonCollection extends AbstractObjectCollection 
{
    public MarketSupplierReviewGatherPersonCollection()
    {
        super(MarketSupplierReviewGatherPersonInfo.class);
    }
    public boolean add(MarketSupplierReviewGatherPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierReviewGatherPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierReviewGatherPersonInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierReviewGatherPersonInfo get(int index)
    {
        return(MarketSupplierReviewGatherPersonInfo)getObject(index);
    }
    public MarketSupplierReviewGatherPersonInfo get(Object key)
    {
        return(MarketSupplierReviewGatherPersonInfo)getObject(key);
    }
    public void set(int index, MarketSupplierReviewGatherPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierReviewGatherPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierReviewGatherPersonInfo item)
    {
        return super.indexOf(item);
    }
}