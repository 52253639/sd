package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketSupplierAppraiseTemplateTreeCollection extends AbstractObjectCollection 
{
    public MarketSupplierAppraiseTemplateTreeCollection()
    {
        super(MarketSupplierAppraiseTemplateTreeInfo.class);
    }
    public boolean add(MarketSupplierAppraiseTemplateTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketSupplierAppraiseTemplateTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketSupplierAppraiseTemplateTreeInfo item)
    {
        return removeObject(item);
    }
    public MarketSupplierAppraiseTemplateTreeInfo get(int index)
    {
        return(MarketSupplierAppraiseTemplateTreeInfo)getObject(index);
    }
    public MarketSupplierAppraiseTemplateTreeInfo get(Object key)
    {
        return(MarketSupplierAppraiseTemplateTreeInfo)getObject(key);
    }
    public void set(int index, MarketSupplierAppraiseTemplateTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketSupplierAppraiseTemplateTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketSupplierAppraiseTemplateTreeInfo item)
    {
        return super.indexOf(item);
    }
}