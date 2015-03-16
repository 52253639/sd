package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RentRemissionCollection extends AbstractObjectCollection 
{
    public RentRemissionCollection()
    {
        super(RentRemissionInfo.class);
    }
    public boolean add(RentRemissionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RentRemissionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RentRemissionInfo item)
    {
        return removeObject(item);
    }
    public RentRemissionInfo get(int index)
    {
        return(RentRemissionInfo)getObject(index);
    }
    public RentRemissionInfo get(Object key)
    {
        return(RentRemissionInfo)getObject(key);
    }
    public void set(int index, RentRemissionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RentRemissionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RentRemissionInfo item)
    {
        return super.indexOf(item);
    }
}