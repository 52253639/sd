package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierEvaluationContractEntryCollection extends AbstractObjectCollection 
{
    public SupplierEvaluationContractEntryCollection()
    {
        super(SupplierEvaluationContractEntryInfo.class);
    }
    public boolean add(SupplierEvaluationContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierEvaluationContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierEvaluationContractEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierEvaluationContractEntryInfo get(int index)
    {
        return(SupplierEvaluationContractEntryInfo)getObject(index);
    }
    public SupplierEvaluationContractEntryInfo get(Object key)
    {
        return(SupplierEvaluationContractEntryInfo)getObject(key);
    }
    public void set(int index, SupplierEvaluationContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierEvaluationContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierEvaluationContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}