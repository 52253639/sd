package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValueInputEntryCollection extends AbstractObjectCollection 
{
    public ValueInputEntryCollection()
    {
        super(ValueInputEntryInfo.class);
    }
    public boolean add(ValueInputEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValueInputEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValueInputEntryInfo item)
    {
        return removeObject(item);
    }
    public ValueInputEntryInfo get(int index)
    {
        return(ValueInputEntryInfo)getObject(index);
    }
    public ValueInputEntryInfo get(Object key)
    {
        return(ValueInputEntryInfo)getObject(key);
    }
    public void set(int index, ValueInputEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValueInputEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValueInputEntryInfo item)
    {
        return super.indexOf(item);
    }
}