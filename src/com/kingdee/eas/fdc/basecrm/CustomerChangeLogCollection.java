package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerChangeLogCollection extends AbstractObjectCollection 
{
    public CustomerChangeLogCollection()
    {
        super(CustomerChangeLogInfo.class);
    }
    public boolean add(CustomerChangeLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerChangeLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerChangeLogInfo item)
    {
        return removeObject(item);
    }
    public CustomerChangeLogInfo get(int index)
    {
        return(CustomerChangeLogInfo)getObject(index);
    }
    public CustomerChangeLogInfo get(Object key)
    {
        return(CustomerChangeLogInfo)getObject(key);
    }
    public void set(int index, CustomerChangeLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerChangeLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerChangeLogInfo item)
    {
        return super.indexOf(item);
    }
}