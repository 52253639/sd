package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValueBreakEntryCollection extends AbstractObjectCollection 
{
    public ValueBreakEntryCollection()
    {
        super(ValueBreakEntryInfo.class);
    }
    public boolean add(ValueBreakEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValueBreakEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValueBreakEntryInfo item)
    {
        return removeObject(item);
    }
    public ValueBreakEntryInfo get(int index)
    {
        return(ValueBreakEntryInfo)getObject(index);
    }
    public ValueBreakEntryInfo get(Object key)
    {
        return(ValueBreakEntryInfo)getObject(key);
    }
    public void set(int index, ValueBreakEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValueBreakEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValueBreakEntryInfo item)
    {
        return super.indexOf(item);
    }
}