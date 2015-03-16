package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DefaultAmountMangerCollection extends AbstractObjectCollection 
{
    public DefaultAmountMangerCollection()
    {
        super(DefaultAmountMangerInfo.class);
    }
    public boolean add(DefaultAmountMangerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DefaultAmountMangerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DefaultAmountMangerInfo item)
    {
        return removeObject(item);
    }
    public DefaultAmountMangerInfo get(int index)
    {
        return(DefaultAmountMangerInfo)getObject(index);
    }
    public DefaultAmountMangerInfo get(Object key)
    {
        return(DefaultAmountMangerInfo)getObject(key);
    }
    public void set(int index, DefaultAmountMangerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DefaultAmountMangerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DefaultAmountMangerInfo item)
    {
        return super.indexOf(item);
    }
}