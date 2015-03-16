package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierReviewGatherContractEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierReviewGatherContractEntryCollection()
    {
        super(MarketSupplierReviewGatherContractEntryInfo.class);
    }
    public boolean add(MarketSupplierReviewGatherContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierReviewGatherContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierReviewGatherContractEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierReviewGatherContractEntryInfo get(int index)
    {
        return(MarketSupplierReviewGatherContractEntryInfo)getObject(index);
    }
    public MarketSupplierReviewGatherContractEntryInfo get(Object key)
    {
        return(MarketSupplierReviewGatherContractEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierReviewGatherContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierReviewGatherContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierReviewGatherContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}