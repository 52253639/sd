package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierReviewGatherEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierReviewGatherEntryCollection()
    {
        super(MarketSupplierReviewGatherEntryInfo.class);
    }
    public boolean add(MarketSupplierReviewGatherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierReviewGatherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierReviewGatherEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierReviewGatherEntryInfo get(int index)
    {
        return(MarketSupplierReviewGatherEntryInfo)getObject(index);
    }
    public MarketSupplierReviewGatherEntryInfo get(Object key)
    {
        return(MarketSupplierReviewGatherEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierReviewGatherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierReviewGatherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierReviewGatherEntryInfo item)
    {
        return super.indexOf(item);
    }
}