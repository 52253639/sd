package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierEvaluationContractEntryCollection extends AbstractObjectCollection 
{
    public MarketSupplierEvaluationContractEntryCollection()
    {
        super(MarketSupplierEvaluationContractEntryInfo.class);
    }
    public boolean add(MarketSupplierEvaluationContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierEvaluationContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierEvaluationContractEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierEvaluationContractEntryInfo get(int index)
    {
        return(MarketSupplierEvaluationContractEntryInfo)getObject(index);
    }
    public MarketSupplierEvaluationContractEntryInfo get(Object key)
    {
        return(MarketSupplierEvaluationContractEntryInfo)getObject(key);
    }
    public void set(int index, MarketSupplierEvaluationContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierEvaluationContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierEvaluationContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}