package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PriceSetSchemeEntryCollection extends AbstractObjectCollection 
{
    public PriceSetSchemeEntryCollection()
    {
        super(PriceSetSchemeEntryInfo.class);
    }
    public boolean add(PriceSetSchemeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PriceSetSchemeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PriceSetSchemeEntryInfo item)
    {
        return removeObject(item);
    }
    public PriceSetSchemeEntryInfo get(int index)
    {
        return(PriceSetSchemeEntryInfo)getObject(index);
    }
    public PriceSetSchemeEntryInfo get(Object key)
    {
        return(PriceSetSchemeEntryInfo)getObject(key);
    }
    public void set(int index, PriceSetSchemeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PriceSetSchemeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PriceSetSchemeEntryInfo item)
    {
        return super.indexOf(item);
    }
}