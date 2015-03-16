package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OldAttachDealAmountEntryCollection extends AbstractObjectCollection 
{
    public OldAttachDealAmountEntryCollection()
    {
        super(OldAttachDealAmountEntryInfo.class);
    }
    public boolean add(OldAttachDealAmountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OldAttachDealAmountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OldAttachDealAmountEntryInfo item)
    {
        return removeObject(item);
    }
    public OldAttachDealAmountEntryInfo get(int index)
    {
        return(OldAttachDealAmountEntryInfo)getObject(index);
    }
    public OldAttachDealAmountEntryInfo get(Object key)
    {
        return(OldAttachDealAmountEntryInfo)getObject(key);
    }
    public void set(int index, OldAttachDealAmountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OldAttachDealAmountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OldAttachDealAmountEntryInfo item)
    {
        return super.indexOf(item);
    }
}