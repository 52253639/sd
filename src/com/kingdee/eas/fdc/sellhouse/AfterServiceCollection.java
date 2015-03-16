package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AfterServiceCollection extends AbstractObjectCollection 
{
    public AfterServiceCollection()
    {
        super(AfterServiceInfo.class);
    }
    public boolean add(AfterServiceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AfterServiceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AfterServiceInfo item)
    {
        return removeObject(item);
    }
    public AfterServiceInfo get(int index)
    {
        return(AfterServiceInfo)getObject(index);
    }
    public AfterServiceInfo get(Object key)
    {
        return(AfterServiceInfo)getObject(key);
    }
    public void set(int index, AfterServiceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AfterServiceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AfterServiceInfo item)
    {
        return super.indexOf(item);
    }
}