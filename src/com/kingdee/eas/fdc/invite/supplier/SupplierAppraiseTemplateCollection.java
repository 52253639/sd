package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierAppraiseTemplateCollection extends AbstractObjectCollection 
{
    public SupplierAppraiseTemplateCollection()
    {
        super(SupplierAppraiseTemplateInfo.class);
    }
    public boolean add(SupplierAppraiseTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierAppraiseTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierAppraiseTemplateInfo item)
    {
        return removeObject(item);
    }
    public SupplierAppraiseTemplateInfo get(int index)
    {
        return(SupplierAppraiseTemplateInfo)getObject(index);
    }
    public SupplierAppraiseTemplateInfo get(Object key)
    {
        return(SupplierAppraiseTemplateInfo)getObject(key);
    }
    public void set(int index, SupplierAppraiseTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierAppraiseTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierAppraiseTemplateInfo item)
    {
        return super.indexOf(item);
    }
}