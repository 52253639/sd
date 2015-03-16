package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierEvaluationCollection extends AbstractObjectCollection 
{
    public MarketSupplierEvaluationCollection()
    {
        super(MarketSupplierEvaluationInfo.class);
    }
    public boolean add(MarketSupplierEvaluationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierEvaluationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierEvaluationInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierEvaluationInfo get(int index)
    {
        return(MarketSupplierEvaluationInfo)getObject(index);
    }
    public MarketSupplierEvaluationInfo get(Object key)
    {
        return(MarketSupplierEvaluationInfo)getObject(key);
    }
    public void set(int index, MarketSupplierEvaluationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierEvaluationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierEvaluationInfo item)
    {
        return super.indexOf(item);
    }
}