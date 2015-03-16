package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewQuotingPriceCollection extends AbstractObjectCollection 
{
    public NewQuotingPriceCollection()
    {
        super(NewQuotingPriceInfo.class);
    }
    public boolean add(NewQuotingPriceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewQuotingPriceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewQuotingPriceInfo item)
    {
        return removeObject(item);
    }
    public NewQuotingPriceInfo get(int index)
    {
        return(NewQuotingPriceInfo)getObject(index);
    }
    public NewQuotingPriceInfo get(Object key)
    {
        return(NewQuotingPriceInfo)getObject(key);
    }
    public void set(int index, NewQuotingPriceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewQuotingPriceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewQuotingPriceInfo item)
    {
        return super.indexOf(item);
    }
}