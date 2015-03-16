package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketUnitSaleManCollection extends AbstractObjectCollection 
{
    public MarketUnitSaleManCollection()
    {
        super(MarketUnitSaleManInfo.class);
    }
    public boolean add(MarketUnitSaleManInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketUnitSaleManCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketUnitSaleManInfo item)
    {
        return removeObject(item);
    }
    public MarketUnitSaleManInfo get(int index)
    {
        return(MarketUnitSaleManInfo)getObject(index);
    }
    public MarketUnitSaleManInfo get(Object key)
    {
        return(MarketUnitSaleManInfo)getObject(key);
    }
    public void set(int index, MarketUnitSaleManInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketUnitSaleManInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketUnitSaleManInfo item)
    {
        return super.indexOf(item);
    }
}