package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCMainCustomerCollection extends AbstractObjectCollection 
{
    public FDCMainCustomerCollection()
    {
        super(FDCMainCustomerInfo.class);
    }
    public boolean add(FDCMainCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCMainCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCMainCustomerInfo item)
    {
        return removeObject(item);
    }
    public FDCMainCustomerInfo get(int index)
    {
        return(FDCMainCustomerInfo)getObject(index);
    }
    public FDCMainCustomerInfo get(Object key)
    {
        return(FDCMainCustomerInfo)getObject(key);
    }
    public void set(int index, FDCMainCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCMainCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCMainCustomerInfo item)
    {
        return super.indexOf(item);
    }
}