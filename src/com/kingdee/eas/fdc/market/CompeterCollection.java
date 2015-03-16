package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompeterCollection extends AbstractObjectCollection 
{
    public CompeterCollection()
    {
        super(CompeterInfo.class);
    }
    public boolean add(CompeterInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompeterCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompeterInfo item)
    {
        return removeObject(item);
    }
    public CompeterInfo get(int index)
    {
        return(CompeterInfo)getObject(index);
    }
    public CompeterInfo get(Object key)
    {
        return(CompeterInfo)getObject(key);
    }
    public void set(int index, CompeterInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompeterInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompeterInfo item)
    {
        return super.indexOf(item);
    }
}