package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierReviewGatherCollection extends AbstractObjectCollection 
{
    public SupplierReviewGatherCollection()
    {
        super(SupplierReviewGatherInfo.class);
    }
    public boolean add(SupplierReviewGatherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierReviewGatherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierReviewGatherInfo item)
    {
        return removeObject(item);
    }
    public SupplierReviewGatherInfo get(int index)
    {
        return(SupplierReviewGatherInfo)getObject(index);
    }
    public SupplierReviewGatherInfo get(Object key)
    {
        return(SupplierReviewGatherInfo)getObject(key);
    }
    public void set(int index, SupplierReviewGatherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierReviewGatherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierReviewGatherInfo item)
    {
        return super.indexOf(item);
    }
}