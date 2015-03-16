package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierReviewGatherEntryCollection extends AbstractObjectCollection 
{
    public SupplierReviewGatherEntryCollection()
    {
        super(SupplierReviewGatherEntryInfo.class);
    }
    public boolean add(SupplierReviewGatherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierReviewGatherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierReviewGatherEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierReviewGatherEntryInfo get(int index)
    {
        return(SupplierReviewGatherEntryInfo)getObject(index);
    }
    public SupplierReviewGatherEntryInfo get(Object key)
    {
        return(SupplierReviewGatherEntryInfo)getObject(key);
    }
    public void set(int index, SupplierReviewGatherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierReviewGatherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierReviewGatherEntryInfo item)
    {
        return super.indexOf(item);
    }
}