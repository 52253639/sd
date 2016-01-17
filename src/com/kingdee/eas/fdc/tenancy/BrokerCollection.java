package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BrokerCollection extends AbstractObjectCollection 
{
    public BrokerCollection()
    {
        super(BrokerInfo.class);
    }
    public boolean add(BrokerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BrokerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BrokerInfo item)
    {
        return removeObject(item);
    }
    public BrokerInfo get(int index)
    {
        return(BrokerInfo)getObject(index);
    }
    public BrokerInfo get(Object key)
    {
        return(BrokerInfo)getObject(key);
    }
    public void set(int index, BrokerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BrokerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BrokerInfo item)
    {
        return super.indexOf(item);
    }
}