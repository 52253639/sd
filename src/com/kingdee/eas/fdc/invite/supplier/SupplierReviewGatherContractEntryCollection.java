package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierReviewGatherContractEntryCollection extends AbstractObjectCollection 
{
    public SupplierReviewGatherContractEntryCollection()
    {
        super(SupplierReviewGatherContractEntryInfo.class);
    }
    public boolean add(SupplierReviewGatherContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierReviewGatherContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierReviewGatherContractEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierReviewGatherContractEntryInfo get(int index)
    {
        return(SupplierReviewGatherContractEntryInfo)getObject(index);
    }
    public SupplierReviewGatherContractEntryInfo get(Object key)
    {
        return(SupplierReviewGatherContractEntryInfo)getObject(key);
    }
    public void set(int index, SupplierReviewGatherContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierReviewGatherContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierReviewGatherContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}