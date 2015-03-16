package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierReviewGatherEvaluationEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierReviewGatherEvaluationEntryCollection()
    {
        super(MarketSupplierReviewGatherEvaluationEntryInfo.class);
    }
    public boolean add(MarketSupplierReviewGatherEvaluationEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierReviewGatherEvaluationEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierReviewGatherEvaluationEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierReviewGatherEvaluationEntryInfo get(int index)
    {
        return(MarketSupplierReviewGatherEvaluationEntryInfo)getObject(index);
    }
    public MarketSupplierReviewGatherEvaluationEntryInfo get(Object key)
    {
        return(MarketSupplierReviewGatherEvaluationEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierReviewGatherEvaluationEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierReviewGatherEvaluationEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierReviewGatherEvaluationEntryInfo item)
    {
        return super.indexOf(item);
    }
}