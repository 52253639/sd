package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DefaultAmountCreatCollection extends AbstractObjectCollection 
{
    public DefaultAmountCreatCollection()
    {
        super(DefaultAmountCreatInfo.class);
    }
    public boolean add(DefaultAmountCreatInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DefaultAmountCreatCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DefaultAmountCreatInfo item)
    {
        return removeObject(item);
    }
    public DefaultAmountCreatInfo get(int index)
    {
        return(DefaultAmountCreatInfo)getObject(index);
    }
    public DefaultAmountCreatInfo get(Object key)
    {
        return(DefaultAmountCreatInfo)getObject(key);
    }
    public void set(int index, DefaultAmountCreatInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DefaultAmountCreatInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DefaultAmountCreatInfo item)
    {
        return super.indexOf(item);
    }
}