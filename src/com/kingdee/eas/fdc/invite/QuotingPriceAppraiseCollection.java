package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuotingPriceAppraiseCollection extends AbstractObjectCollection 
{
    public QuotingPriceAppraiseCollection()
    {
        super(QuotingPriceAppraiseInfo.class);
    }
    public boolean add(QuotingPriceAppraiseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuotingPriceAppraiseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuotingPriceAppraiseInfo item)
    {
        return removeObject(item);
    }
    public QuotingPriceAppraiseInfo get(int index)
    {
        return(QuotingPriceAppraiseInfo)getObject(index);
    }
    public QuotingPriceAppraiseInfo get(Object key)
    {
        return(QuotingPriceAppraiseInfo)getObject(key);
    }
    public void set(int index, QuotingPriceAppraiseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuotingPriceAppraiseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuotingPriceAppraiseInfo item)
    {
        return super.indexOf(item);
    }
}