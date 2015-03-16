package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompeteItemPriceInfoEntryCollection extends AbstractObjectCollection 
{
    public CompeteItemPriceInfoEntryCollection()
    {
        super(CompeteItemPriceInfoEntryInfo.class);
    }
    public boolean add(CompeteItemPriceInfoEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompeteItemPriceInfoEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompeteItemPriceInfoEntryInfo item)
    {
        return removeObject(item);
    }
    public CompeteItemPriceInfoEntryInfo get(int index)
    {
        return(CompeteItemPriceInfoEntryInfo)getObject(index);
    }
    public CompeteItemPriceInfoEntryInfo get(Object key)
    {
        return(CompeteItemPriceInfoEntryInfo)getObject(key);
    }
    public void set(int index, CompeteItemPriceInfoEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompeteItemPriceInfoEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompeteItemPriceInfoEntryInfo item)
    {
        return super.indexOf(item);
    }
}