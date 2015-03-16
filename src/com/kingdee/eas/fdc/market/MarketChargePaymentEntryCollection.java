package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketChargePaymentEntryCollection extends AbstractObjectCollection 
{
    public MarketChargePaymentEntryCollection()
    {
        super(MarketChargePaymentEntryInfo.class);
    }
    public boolean add(MarketChargePaymentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketChargePaymentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketChargePaymentEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketChargePaymentEntryInfo get(int index)
    {
        return(MarketChargePaymentEntryInfo)getObject(index);
    }
    public MarketChargePaymentEntryInfo get(Object key)
    {
        return(MarketChargePaymentEntryInfo)getObject(key);
    }
    public void set(int index, MarketChargePaymentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketChargePaymentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketChargePaymentEntryInfo item)
    {
        return super.indexOf(item);
    }
}