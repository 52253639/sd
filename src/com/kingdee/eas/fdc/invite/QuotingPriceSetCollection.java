package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuotingPriceSetCollection extends AbstractObjectCollection 
{
    public QuotingPriceSetCollection()
    {
        super(QuotingPriceSetInfo.class);
    }
    public boolean add(QuotingPriceSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuotingPriceSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuotingPriceSetInfo item)
    {
        return removeObject(item);
    }
    public QuotingPriceSetInfo get(int index)
    {
        return(QuotingPriceSetInfo)getObject(index);
    }
    public QuotingPriceSetInfo get(Object key)
    {
        return(QuotingPriceSetInfo)getObject(key);
    }
    public void set(int index, QuotingPriceSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuotingPriceSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuotingPriceSetInfo item)
    {
        return super.indexOf(item);
    }
}