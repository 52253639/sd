package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DefaultAmountMangerEntryCollection extends AbstractObjectCollection 
{
    public DefaultAmountMangerEntryCollection()
    {
        super(DefaultAmountMangerEntryInfo.class);
    }
    public boolean add(DefaultAmountMangerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DefaultAmountMangerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DefaultAmountMangerEntryInfo item)
    {
        return removeObject(item);
    }
    public DefaultAmountMangerEntryInfo get(int index)
    {
        return(DefaultAmountMangerEntryInfo)getObject(index);
    }
    public DefaultAmountMangerEntryInfo get(Object key)
    {
        return(DefaultAmountMangerEntryInfo)getObject(key);
    }
    public void set(int index, DefaultAmountMangerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DefaultAmountMangerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DefaultAmountMangerEntryInfo item)
    {
        return super.indexOf(item);
    }
}