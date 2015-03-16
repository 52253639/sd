package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEFunctionSetEntryCollection extends AbstractObjectCollection 
{
    public SHEFunctionSetEntryCollection()
    {
        super(SHEFunctionSetEntryInfo.class);
    }
    public boolean add(SHEFunctionSetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEFunctionSetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEFunctionSetEntryInfo item)
    {
        return removeObject(item);
    }
    public SHEFunctionSetEntryInfo get(int index)
    {
        return(SHEFunctionSetEntryInfo)getObject(index);
    }
    public SHEFunctionSetEntryInfo get(Object key)
    {
        return(SHEFunctionSetEntryInfo)getObject(key);
    }
    public void set(int index, SHEFunctionSetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEFunctionSetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEFunctionSetEntryInfo item)
    {
        return super.indexOf(item);
    }
}