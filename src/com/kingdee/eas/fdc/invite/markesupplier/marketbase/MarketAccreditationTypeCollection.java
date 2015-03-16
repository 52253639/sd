package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketAccreditationTypeCollection extends AbstractObjectCollection 
{
    public MarketAccreditationTypeCollection()
    {
        super(MarketAccreditationTypeInfo.class);
    }
    public boolean add(MarketAccreditationTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketAccreditationTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketAccreditationTypeInfo item)
    {
        return removeObject(item);
    }
    public MarketAccreditationTypeInfo get(int index)
    {
        return(MarketAccreditationTypeInfo)getObject(index);
    }
    public MarketAccreditationTypeInfo get(Object key)
    {
        return(MarketAccreditationTypeInfo)getObject(key);
    }
    public void set(int index, MarketAccreditationTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketAccreditationTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketAccreditationTypeInfo item)
    {
        return super.indexOf(item);
    }
}