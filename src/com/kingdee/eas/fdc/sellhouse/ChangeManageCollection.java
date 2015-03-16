package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeManageCollection extends AbstractObjectCollection 
{
    public ChangeManageCollection()
    {
        super(ChangeManageInfo.class);
    }
    public boolean add(ChangeManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeManageInfo item)
    {
        return removeObject(item);
    }
    public ChangeManageInfo get(int index)
    {
        return(ChangeManageInfo)getObject(index);
    }
    public ChangeManageInfo get(Object key)
    {
        return(ChangeManageInfo)getObject(key);
    }
    public void set(int index, ChangeManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeManageInfo item)
    {
        return super.indexOf(item);
    }
}