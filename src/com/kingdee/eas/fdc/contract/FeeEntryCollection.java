package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FeeEntryCollection extends AbstractObjectCollection 
{
    public FeeEntryCollection()
    {
        super(FeeEntryInfo.class);
    }
    public boolean add(FeeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FeeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FeeEntryInfo item)
    {
        return removeObject(item);
    }
    public FeeEntryInfo get(int index)
    {
        return(FeeEntryInfo)getObject(index);
    }
    public FeeEntryInfo get(Object key)
    {
        return(FeeEntryInfo)getObject(key);
    }
    public void set(int index, FeeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FeeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FeeEntryInfo item)
    {
        return super.indexOf(item);
    }
}