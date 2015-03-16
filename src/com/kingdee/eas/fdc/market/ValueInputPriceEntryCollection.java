package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValueInputPriceEntryCollection extends AbstractObjectCollection 
{
    public ValueInputPriceEntryCollection()
    {
        super(ValueInputPriceEntryInfo.class);
    }
    public boolean add(ValueInputPriceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValueInputPriceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValueInputPriceEntryInfo item)
    {
        return removeObject(item);
    }
    public ValueInputPriceEntryInfo get(int index)
    {
        return(ValueInputPriceEntryInfo)getObject(index);
    }
    public ValueInputPriceEntryInfo get(Object key)
    {
        return(ValueInputPriceEntryInfo)getObject(key);
    }
    public void set(int index, ValueInputPriceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValueInputPriceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValueInputPriceEntryInfo item)
    {
        return super.indexOf(item);
    }
}