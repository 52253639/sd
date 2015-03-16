package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierEvaluationTypeCollection extends AbstractObjectCollection 
{
    public SupplierEvaluationTypeCollection()
    {
        super(SupplierEvaluationTypeInfo.class);
    }
    public boolean add(SupplierEvaluationTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierEvaluationTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierEvaluationTypeInfo item)
    {
        return removeObject(item);
    }
    public SupplierEvaluationTypeInfo get(int index)
    {
        return(SupplierEvaluationTypeInfo)getObject(index);
    }
    public SupplierEvaluationTypeInfo get(Object key)
    {
        return(SupplierEvaluationTypeInfo)getObject(key);
    }
    public void set(int index, SupplierEvaluationTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierEvaluationTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierEvaluationTypeInfo item)
    {
        return super.indexOf(item);
    }
}