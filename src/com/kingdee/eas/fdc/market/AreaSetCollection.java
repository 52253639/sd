package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AreaSetCollection extends AbstractObjectCollection 
{
    public AreaSetCollection()
    {
        super(AreaSetInfo.class);
    }
    public boolean add(AreaSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AreaSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AreaSetInfo item)
    {
        return removeObject(item);
    }
    public AreaSetInfo get(int index)
    {
        return(AreaSetInfo)getObject(index);
    }
    public AreaSetInfo get(Object key)
    {
        return(AreaSetInfo)getObject(key);
    }
    public void set(int index, AreaSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AreaSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AreaSetInfo item)
    {
        return super.indexOf(item);
    }
}