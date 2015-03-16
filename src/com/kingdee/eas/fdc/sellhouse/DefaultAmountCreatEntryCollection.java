package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DefaultAmountCreatEntryCollection extends AbstractObjectCollection 
{
    public DefaultAmountCreatEntryCollection()
    {
        super(DefaultAmountCreatEntryInfo.class);
    }
    public boolean add(DefaultAmountCreatEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DefaultAmountCreatEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DefaultAmountCreatEntryInfo item)
    {
        return removeObject(item);
    }
    public DefaultAmountCreatEntryInfo get(int index)
    {
        return(DefaultAmountCreatEntryInfo)getObject(index);
    }
    public DefaultAmountCreatEntryInfo get(Object key)
    {
        return(DefaultAmountCreatEntryInfo)getObject(key);
    }
    public void set(int index, DefaultAmountCreatEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DefaultAmountCreatEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DefaultAmountCreatEntryInfo item)
    {
        return super.indexOf(item);
    }
}