package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RefPriceEntryCollection extends AbstractObjectCollection 
{
    public RefPriceEntryCollection()
    {
        super(RefPriceEntryInfo.class);
    }
    public boolean add(RefPriceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RefPriceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RefPriceEntryInfo item)
    {
        return removeObject(item);
    }
    public RefPriceEntryInfo get(int index)
    {
        return(RefPriceEntryInfo)getObject(index);
    }
    public RefPriceEntryInfo get(Object key)
    {
        return(RefPriceEntryInfo)getObject(key);
    }
    public void set(int index, RefPriceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RefPriceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RefPriceEntryInfo item)
    {
        return super.indexOf(item);
    }
}