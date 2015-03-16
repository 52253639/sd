package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierEvaluationAuditResultCollection extends AbstractObjectCollection 
{
    public MarketSupplierEvaluationAuditResultCollection()
    {
        super(MarketSupplierEvaluationAuditResultInfo.class);
    }
    public boolean add(MarketSupplierEvaluationAuditResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierEvaluationAuditResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierEvaluationAuditResultInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierEvaluationAuditResultInfo get(int index)
    {
        return(MarketSupplierEvaluationAuditResultInfo)getObject(index);
    }
    public MarketSupplierEvaluationAuditResultInfo get(Object key)
    {
        return(MarketSupplierEvaluationAuditResultInfo)getObject(key);
    }
    public void set(int index, MarketSupplierEvaluationAuditResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierEvaluationAuditResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierEvaluationAuditResultInfo item)
    {
        return super.indexOf(item);
    }
}