package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketManageEffectCollection extends AbstractObjectCollection 
{
    public MarketManageEffectCollection()
    {
        super(MarketManageEffectInfo.class);
    }
    public boolean add(MarketManageEffectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketManageEffectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketManageEffectInfo item)
    {
        return removeObject(item);
    }
    public MarketManageEffectInfo get(int index)
    {
        return(MarketManageEffectInfo)getObject(index);
    }
    public MarketManageEffectInfo get(Object key)
    {
        return(MarketManageEffectInfo)getObject(key);
    }
    public void set(int index, MarketManageEffectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketManageEffectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketManageEffectInfo item)
    {
        return super.indexOf(item);
    }
}