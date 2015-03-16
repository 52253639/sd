package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PriceSetSchemeCollection extends AbstractObjectCollection 
{
    public PriceSetSchemeCollection()
    {
        super(PriceSetSchemeInfo.class);
    }
    public boolean add(PriceSetSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PriceSetSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PriceSetSchemeInfo item)
    {
        return removeObject(item);
    }
    public PriceSetSchemeInfo get(int index)
    {
        return(PriceSetSchemeInfo)getObject(index);
    }
    public PriceSetSchemeInfo get(Object key)
    {
        return(PriceSetSchemeInfo)getObject(key);
    }
    public void set(int index, PriceSetSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PriceSetSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PriceSetSchemeInfo item)
    {
        return super.indexOf(item);
    }
}