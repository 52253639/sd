package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewAttachDealAmountEntryCollection extends AbstractObjectCollection 
{
    public NewAttachDealAmountEntryCollection()
    {
        super(NewAttachDealAmountEntryInfo.class);
    }
    public boolean add(NewAttachDealAmountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewAttachDealAmountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewAttachDealAmountEntryInfo item)
    {
        return removeObject(item);
    }
    public NewAttachDealAmountEntryInfo get(int index)
    {
        return(NewAttachDealAmountEntryInfo)getObject(index);
    }
    public NewAttachDealAmountEntryInfo get(Object key)
    {
        return(NewAttachDealAmountEntryInfo)getObject(key);
    }
    public void set(int index, NewAttachDealAmountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewAttachDealAmountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewAttachDealAmountEntryInfo item)
    {
        return super.indexOf(item);
    }
}