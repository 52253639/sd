package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GlobalTaskNodeCollection extends AbstractObjectCollection 
{
    public GlobalTaskNodeCollection()
    {
        super(GlobalTaskNodeInfo.class);
    }
    public boolean add(GlobalTaskNodeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GlobalTaskNodeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GlobalTaskNodeInfo item)
    {
        return removeObject(item);
    }
    public GlobalTaskNodeInfo get(int index)
    {
        return(GlobalTaskNodeInfo)getObject(index);
    }
    public GlobalTaskNodeInfo get(Object key)
    {
        return(GlobalTaskNodeInfo)getObject(key);
    }
    public void set(int index, GlobalTaskNodeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GlobalTaskNodeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GlobalTaskNodeInfo item)
    {
        return super.indexOf(item);
    }
}