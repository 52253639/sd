package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChequeSellProjectEntryCollection extends AbstractObjectCollection 
{
    public ChequeSellProjectEntryCollection()
    {
        super(ChequeSellProjectEntryInfo.class);
    }
    public boolean add(ChequeSellProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChequeSellProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChequeSellProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public ChequeSellProjectEntryInfo get(int index)
    {
        return(ChequeSellProjectEntryInfo)getObject(index);
    }
    public ChequeSellProjectEntryInfo get(Object key)
    {
        return(ChequeSellProjectEntryInfo)getObject(key);
    }
    public void set(int index, ChequeSellProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChequeSellProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChequeSellProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}