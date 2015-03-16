package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachDealAmountEntryCollection extends AbstractObjectCollection 
{
    public AttachDealAmountEntryCollection()
    {
        super(AttachDealAmountEntryInfo.class);
    }
    public boolean add(AttachDealAmountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachDealAmountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachDealAmountEntryInfo item)
    {
        return removeObject(item);
    }
    public AttachDealAmountEntryInfo get(int index)
    {
        return(AttachDealAmountEntryInfo)getObject(index);
    }
    public AttachDealAmountEntryInfo get(Object key)
    {
        return(AttachDealAmountEntryInfo)getObject(key);
    }
    public void set(int index, AttachDealAmountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachDealAmountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachDealAmountEntryInfo item)
    {
        return super.indexOf(item);
    }
}