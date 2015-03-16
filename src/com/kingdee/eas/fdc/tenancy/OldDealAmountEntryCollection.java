package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OldDealAmountEntryCollection extends AbstractObjectCollection 
{
    public OldDealAmountEntryCollection()
    {
        super(OldDealAmountEntryInfo.class);
    }
    public boolean add(OldDealAmountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OldDealAmountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OldDealAmountEntryInfo item)
    {
        return removeObject(item);
    }
    public OldDealAmountEntryInfo get(int index)
    {
        return(OldDealAmountEntryInfo)getObject(index);
    }
    public OldDealAmountEntryInfo get(Object key)
    {
        return(OldDealAmountEntryInfo)getObject(key);
    }
    public void set(int index, OldDealAmountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OldDealAmountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OldDealAmountEntryInfo item)
    {
        return super.indexOf(item);
    }
}