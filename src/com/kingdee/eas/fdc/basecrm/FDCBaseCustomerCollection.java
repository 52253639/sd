package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBaseCustomerCollection extends AbstractObjectCollection 
{
    public FDCBaseCustomerCollection()
    {
        super(FDCBaseCustomerInfo.class);
    }
    public boolean add(FDCBaseCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBaseCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBaseCustomerInfo item)
    {
        return removeObject(item);
    }
    public FDCBaseCustomerInfo get(int index)
    {
        return(FDCBaseCustomerInfo)getObject(index);
    }
    public FDCBaseCustomerInfo get(Object key)
    {
        return(FDCBaseCustomerInfo)getObject(key);
    }
    public void set(int index, FDCBaseCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBaseCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBaseCustomerInfo item)
    {
        return super.indexOf(item);
    }
}