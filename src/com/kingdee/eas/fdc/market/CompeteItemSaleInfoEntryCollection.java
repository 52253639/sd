package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompeteItemSaleInfoEntryCollection extends AbstractObjectCollection 
{
    public CompeteItemSaleInfoEntryCollection()
    {
        super(CompeteItemSaleInfoEntryInfo.class);
    }
    public boolean add(CompeteItemSaleInfoEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompeteItemSaleInfoEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompeteItemSaleInfoEntryInfo item)
    {
        return removeObject(item);
    }
    public CompeteItemSaleInfoEntryInfo get(int index)
    {
        return(CompeteItemSaleInfoEntryInfo)getObject(index);
    }
    public CompeteItemSaleInfoEntryInfo get(Object key)
    {
        return(CompeteItemSaleInfoEntryInfo)getObject(key);
    }
    public void set(int index, CompeteItemSaleInfoEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompeteItemSaleInfoEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompeteItemSaleInfoEntryInfo item)
    {
        return super.indexOf(item);
    }
}