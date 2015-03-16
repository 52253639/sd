package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HandleTenancyCollection extends AbstractObjectCollection 
{
    public HandleTenancyCollection()
    {
        super(HandleTenancyInfo.class);
    }
    public boolean add(HandleTenancyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HandleTenancyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HandleTenancyInfo item)
    {
        return removeObject(item);
    }
    public HandleTenancyInfo get(int index)
    {
        return(HandleTenancyInfo)getObject(index);
    }
    public HandleTenancyInfo get(Object key)
    {
        return(HandleTenancyInfo)getObject(key);
    }
    public void set(int index, HandleTenancyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HandleTenancyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HandleTenancyInfo item)
    {
        return super.indexOf(item);
    }
}