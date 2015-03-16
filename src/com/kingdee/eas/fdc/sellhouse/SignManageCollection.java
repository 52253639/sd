package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SignManageCollection extends AbstractObjectCollection 
{
    public SignManageCollection()
    {
        super(SignManageInfo.class);
    }
    public boolean add(SignManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SignManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SignManageInfo item)
    {
        return removeObject(item);
    }
    public SignManageInfo get(int index)
    {
        return(SignManageInfo)getObject(index);
    }
    public SignManageInfo get(Object key)
    {
        return(SignManageInfo)getObject(key);
    }
    public void set(int index, SignManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SignManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SignManageInfo item)
    {
        return super.indexOf(item);
    }
}