package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PriceAdjustEntryCollection extends AbstractObjectCollection 
{
    public PriceAdjustEntryCollection()
    {
        super(PriceAdjustEntryInfo.class);
    }
    public boolean add(PriceAdjustEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PriceAdjustEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PriceAdjustEntryInfo item)
    {
        return removeObject(item);
    }
    public PriceAdjustEntryInfo get(int index)
    {
        return(PriceAdjustEntryInfo)getObject(index);
    }
    public PriceAdjustEntryInfo get(Object key)
    {
        return(PriceAdjustEntryInfo)getObject(key);
    }
    public void set(int index, PriceAdjustEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PriceAdjustEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PriceAdjustEntryInfo item)
    {
        return super.indexOf(item);
    }
}