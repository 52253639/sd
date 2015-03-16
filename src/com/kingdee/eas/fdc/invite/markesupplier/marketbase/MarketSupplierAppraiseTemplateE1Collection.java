package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierAppraiseTemplateE1Collection extends AbstractObjectCollection 
{
    public MarketSupplierAppraiseTemplateE1Collection()
    {
        super(MarketSupplierAppraiseTemplateE1Info.class);
    }
    public boolean add(MarketSupplierAppraiseTemplateE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierAppraiseTemplateE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierAppraiseTemplateE1Info item)
    {
        return removeObject(item);
    }
    public MarketSupplierAppraiseTemplateE1Info get(int index)
    {
        return(MarketSupplierAppraiseTemplateE1Info)getObject(index);
    }
    public MarketSupplierAppraiseTemplateE1Info get(Object key)
    {
        return(MarketSupplierAppraiseTemplateE1Info)getObject(key);
    }
    public void set(int index, MarketSupplierAppraiseTemplateE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierAppraiseTemplateE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierAppraiseTemplateE1Info item)
    {
        return super.indexOf(item);
    }
}