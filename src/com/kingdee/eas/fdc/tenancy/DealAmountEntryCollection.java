package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DealAmountEntryCollection extends AbstractObjectCollection 
{
    public DealAmountEntryCollection()
    {
        super(DealAmountEntryInfo.class);
    }
    public boolean add(DealAmountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DealAmountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DealAmountEntryInfo item)
    {
        return removeObject(item);
    }
    public DealAmountEntryInfo get(int index)
    {
        return(DealAmountEntryInfo)getObject(index);
    }
    public DealAmountEntryInfo get(Object key)
    {
        return(DealAmountEntryInfo)getObject(key);
    }
    public void set(int index, DealAmountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DealAmountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DealAmountEntryInfo item)
    {
        return super.indexOf(item);
    }
}