package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CheckNodeCollection extends AbstractObjectCollection 
{
    public CheckNodeCollection()
    {
        super(CheckNodeInfo.class);
    }
    public boolean add(CheckNodeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CheckNodeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CheckNodeInfo item)
    {
        return removeObject(item);
    }
    public CheckNodeInfo get(int index)
    {
        return(CheckNodeInfo)getObject(index);
    }
    public CheckNodeInfo get(Object key)
    {
        return(CheckNodeInfo)getObject(key);
    }
    public void set(int index, CheckNodeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CheckNodeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CheckNodeInfo item)
    {
        return super.indexOf(item);
    }
}