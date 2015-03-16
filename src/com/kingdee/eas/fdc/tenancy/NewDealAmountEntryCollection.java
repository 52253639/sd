package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewDealAmountEntryCollection extends AbstractObjectCollection 
{
    public NewDealAmountEntryCollection()
    {
        super(NewDealAmountEntryInfo.class);
    }
    public boolean add(NewDealAmountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewDealAmountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewDealAmountEntryInfo item)
    {
        return removeObject(item);
    }
    public NewDealAmountEntryInfo get(int index)
    {
        return(NewDealAmountEntryInfo)getObject(index);
    }
    public NewDealAmountEntryInfo get(Object key)
    {
        return(NewDealAmountEntryInfo)getObject(key);
    }
    public void set(int index, NewDealAmountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewDealAmountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewDealAmountEntryInfo item)
    {
        return super.indexOf(item);
    }
}