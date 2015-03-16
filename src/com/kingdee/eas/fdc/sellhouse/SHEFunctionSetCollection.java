package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEFunctionSetCollection extends AbstractObjectCollection 
{
    public SHEFunctionSetCollection()
    {
        super(SHEFunctionSetInfo.class);
    }
    public boolean add(SHEFunctionSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEFunctionSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEFunctionSetInfo item)
    {
        return removeObject(item);
    }
    public SHEFunctionSetInfo get(int index)
    {
        return(SHEFunctionSetInfo)getObject(index);
    }
    public SHEFunctionSetInfo get(Object key)
    {
        return(SHEFunctionSetInfo)getObject(key);
    }
    public void set(int index, SHEFunctionSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEFunctionSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEFunctionSetInfo item)
    {
        return super.indexOf(item);
    }
}