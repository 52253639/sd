package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierAppraiseTemplateCollection extends AbstractObjectCollection 
{
    public MarketSupplierAppraiseTemplateCollection()
    {
        super(MarketSupplierAppraiseTemplateInfo.class);
    }
    public boolean add(MarketSupplierAppraiseTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierAppraiseTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierAppraiseTemplateInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierAppraiseTemplateInfo get(int index)
    {
        return(MarketSupplierAppraiseTemplateInfo)getObject(index);
    }
    public MarketSupplierAppraiseTemplateInfo get(Object key)
    {
        return(MarketSupplierAppraiseTemplateInfo)getObject(key);
    }
    public void set(int index, MarketSupplierAppraiseTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierAppraiseTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierAppraiseTemplateInfo item)
    {
        return super.indexOf(item);
    }
}