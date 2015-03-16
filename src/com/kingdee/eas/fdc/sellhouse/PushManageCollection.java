package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PushManageCollection extends AbstractObjectCollection 
{
    public PushManageCollection()
    {
        super(PushManageInfo.class);
    }
    public boolean add(PushManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PushManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PushManageInfo item)
    {
        return removeObject(item);
    }
    public PushManageInfo get(int index)
    {
        return(PushManageInfo)getObject(index);
    }
    public PushManageInfo get(Object key)
    {
        return(PushManageInfo)getObject(key);
    }
    public void set(int index, PushManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PushManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PushManageInfo item)
    {
        return super.indexOf(item);
    }
}