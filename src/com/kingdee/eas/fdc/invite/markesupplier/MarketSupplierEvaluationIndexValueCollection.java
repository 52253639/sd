package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierEvaluationIndexValueCollection extends AbstractObjectCollection 
{
    public MarketSupplierEvaluationIndexValueCollection()
    {
        super(MarketSupplierEvaluationIndexValueInfo.class);
    }
    public boolean add(MarketSupplierEvaluationIndexValueInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierEvaluationIndexValueCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierEvaluationIndexValueInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierEvaluationIndexValueInfo get(int index)
    {
        return(MarketSupplierEvaluationIndexValueInfo)getObject(index);
    }
    public MarketSupplierEvaluationIndexValueInfo get(Object key)
    {
        return(MarketSupplierEvaluationIndexValueInfo)getObject(key);
    }
    public void set(int index, MarketSupplierEvaluationIndexValueInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierEvaluationIndexValueInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierEvaluationIndexValueInfo item)
    {
        return super.indexOf(item);
    }
}