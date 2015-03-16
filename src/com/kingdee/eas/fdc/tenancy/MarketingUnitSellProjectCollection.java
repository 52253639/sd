package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketingUnitSellProjectCollection extends AbstractObjectCollection 
{
    public MarketingUnitSellProjectCollection()
    {
        super(MarketingUnitSellProjectInfo.class);
    }
    public boolean add(MarketingUnitSellProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketingUnitSellProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketingUnitSellProjectInfo item)
    {
        return removeObject(item);
    }
    public MarketingUnitSellProjectInfo get(int index)
    {
        return(MarketingUnitSellProjectInfo)getObject(index);
    }
    public MarketingUnitSellProjectInfo get(Object key)
    {
        return(MarketingUnitSellProjectInfo)getObject(key);
    }
    public void set(int index, MarketingUnitSellProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketingUnitSellProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketingUnitSellProjectInfo item)
    {
        return super.indexOf(item);
    }
}