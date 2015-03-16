package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuotingPriceGatherCollection extends AbstractObjectCollection 
{
    public QuotingPriceGatherCollection()
    {
        super(QuotingPriceGatherInfo.class);
    }
    public boolean add(QuotingPriceGatherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuotingPriceGatherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuotingPriceGatherInfo item)
    {
        return removeObject(item);
    }
    public QuotingPriceGatherInfo get(int index)
    {
        return(QuotingPriceGatherInfo)getObject(index);
    }
    public QuotingPriceGatherInfo get(Object key)
    {
        return(QuotingPriceGatherInfo)getObject(key);
    }
    public void set(int index, QuotingPriceGatherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuotingPriceGatherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuotingPriceGatherInfo item)
    {
        return super.indexOf(item);
    }
}