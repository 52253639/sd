package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CluesManageCollection extends AbstractObjectCollection 
{
    public CluesManageCollection()
    {
        super(CluesManageInfo.class);
    }
    public boolean add(CluesManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CluesManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CluesManageInfo item)
    {
        return removeObject(item);
    }
    public CluesManageInfo get(int index)
    {
        return(CluesManageInfo)getObject(index);
    }
    public CluesManageInfo get(Object key)
    {
        return(CluesManageInfo)getObject(key);
    }
    public void set(int index, CluesManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CluesManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CluesManageInfo item)
    {
        return super.indexOf(item);
    }
}