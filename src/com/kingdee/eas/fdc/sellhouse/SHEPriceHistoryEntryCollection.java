package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEPriceHistoryEntryCollection extends AbstractObjectCollection 
{
    public SHEPriceHistoryEntryCollection()
    {
        super(SHEPriceHistoryEntryInfo.class);
    }
    public boolean add(SHEPriceHistoryEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEPriceHistoryEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEPriceHistoryEntryInfo item)
    {
        return removeObject(item);
    }
    public SHEPriceHistoryEntryInfo get(int index)
    {
        return(SHEPriceHistoryEntryInfo)getObject(index);
    }
    public SHEPriceHistoryEntryInfo get(Object key)
    {
        return(SHEPriceHistoryEntryInfo)getObject(key);
    }
    public void set(int index, SHEPriceHistoryEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEPriceHistoryEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEPriceHistoryEntryInfo item)
    {
        return super.indexOf(item);
    }
}