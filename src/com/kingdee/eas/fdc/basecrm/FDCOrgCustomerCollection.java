package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCOrgCustomerCollection extends AbstractObjectCollection 
{
    public FDCOrgCustomerCollection()
    {
        super(FDCOrgCustomerInfo.class);
    }
    public boolean add(FDCOrgCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCOrgCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCOrgCustomerInfo item)
    {
        return removeObject(item);
    }
    public FDCOrgCustomerInfo get(int index)
    {
        return(FDCOrgCustomerInfo)getObject(index);
    }
    public FDCOrgCustomerInfo get(Object key)
    {
        return(FDCOrgCustomerInfo)getObject(key);
    }
    public void set(int index, FDCOrgCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCOrgCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCOrgCustomerInfo item)
    {
        return super.indexOf(item);
    }
}