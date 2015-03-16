package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketingUnitCollection extends AbstractObjectCollection 
{
    public MarketingUnitCollection()
    {
        super(MarketingUnitInfo.class);
    }
    public boolean add(MarketingUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketingUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketingUnitInfo item)
    {
        return removeObject(item);
    }
    public MarketingUnitInfo get(int index)
    {
        return(MarketingUnitInfo)getObject(index);
    }
    public MarketingUnitInfo get(Object key)
    {
        return(MarketingUnitInfo)getObject(key);
    }
    public void set(int index, MarketingUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketingUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketingUnitInfo item)
    {
        return super.indexOf(item);
    }
}