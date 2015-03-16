package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEProjectSetCollection extends AbstractObjectCollection 
{
    public SHEProjectSetCollection()
    {
        super(SHEProjectSetInfo.class);
    }
    public boolean add(SHEProjectSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEProjectSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEProjectSetInfo item)
    {
        return removeObject(item);
    }
    public SHEProjectSetInfo get(int index)
    {
        return(SHEProjectSetInfo)getObject(index);
    }
    public SHEProjectSetInfo get(Object key)
    {
        return(SHEProjectSetInfo)getObject(key);
    }
    public void set(int index, SHEProjectSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEProjectSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEProjectSetInfo item)
    {
        return super.indexOf(item);
    }
}