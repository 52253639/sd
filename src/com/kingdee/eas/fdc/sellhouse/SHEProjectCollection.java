package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEProjectCollection extends AbstractObjectCollection 
{
    public SHEProjectCollection()
    {
        super(SHEProjectInfo.class);
    }
    public boolean add(SHEProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEProjectInfo item)
    {
        return removeObject(item);
    }
    public SHEProjectInfo get(int index)
    {
        return(SHEProjectInfo)getObject(index);
    }
    public SHEProjectInfo get(Object key)
    {
        return(SHEProjectInfo)getObject(key);
    }
    public void set(int index, SHEProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEProjectInfo item)
    {
        return super.indexOf(item);
    }
}