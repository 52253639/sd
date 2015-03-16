package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValueInputDYEntryCollection extends AbstractObjectCollection 
{
    public ValueInputDYEntryCollection()
    {
        super(ValueInputDYEntryInfo.class);
    }
    public boolean add(ValueInputDYEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValueInputDYEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValueInputDYEntryInfo item)
    {
        return removeObject(item);
    }
    public ValueInputDYEntryInfo get(int index)
    {
        return(ValueInputDYEntryInfo)getObject(index);
    }
    public ValueInputDYEntryInfo get(Object key)
    {
        return(ValueInputDYEntryInfo)getObject(key);
    }
    public void set(int index, ValueInputDYEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValueInputDYEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValueInputDYEntryInfo item)
    {
        return super.indexOf(item);
    }
}