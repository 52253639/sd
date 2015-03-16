package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RevListCollection extends AbstractObjectCollection 
{
    public RevListCollection()
    {
        super(RevListInfo.class);
    }
    public boolean add(RevListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RevListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RevListInfo item)
    {
        return removeObject(item);
    }
    public RevListInfo get(int index)
    {
        return(RevListInfo)getObject(index);
    }
    public RevListInfo get(Object key)
    {
        return(RevListInfo)getObject(key);
    }
    public void set(int index, RevListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RevListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RevListInfo item)
    {
        return super.indexOf(item);
    }
}