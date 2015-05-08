package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TraEntryCollection extends AbstractObjectCollection 
{
    public TraEntryCollection()
    {
        super(TraEntryInfo.class);
    }
    public boolean add(TraEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TraEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TraEntryInfo item)
    {
        return removeObject(item);
    }
    public TraEntryInfo get(int index)
    {
        return(TraEntryInfo)getObject(index);
    }
    public TraEntryInfo get(Object key)
    {
        return(TraEntryInfo)getObject(key);
    }
    public void set(int index, TraEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TraEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TraEntryInfo item)
    {
        return super.indexOf(item);
    }
}