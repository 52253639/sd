package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCustomerCollection extends AbstractObjectCollection 
{
    public FDCCustomerCollection()
    {
        super(FDCCustomerInfo.class);
    }
    public boolean add(FDCCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCustomerInfo item)
    {
        return removeObject(item);
    }
    public FDCCustomerInfo get(int index)
    {
        return(FDCCustomerInfo)getObject(index);
    }
    public FDCCustomerInfo get(Object key)
    {
        return(FDCCustomerInfo)getObject(key);
    }
    public void set(int index, FDCCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCustomerInfo item)
    {
        return super.indexOf(item);
    }
}