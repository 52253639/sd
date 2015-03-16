package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeveloperManageCollection extends AbstractObjectCollection 
{
    public DeveloperManageCollection()
    {
        super(DeveloperManageInfo.class);
    }
    public boolean add(DeveloperManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeveloperManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeveloperManageInfo item)
    {
        return removeObject(item);
    }
    public DeveloperManageInfo get(int index)
    {
        return(DeveloperManageInfo)getObject(index);
    }
    public DeveloperManageInfo get(Object key)
    {
        return(DeveloperManageInfo)getObject(key);
    }
    public void set(int index, DeveloperManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeveloperManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeveloperManageInfo item)
    {
        return super.indexOf(item);
    }
}