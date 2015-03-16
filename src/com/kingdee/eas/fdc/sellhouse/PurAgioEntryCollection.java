package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurAgioEntryCollection extends AbstractObjectCollection 
{
    public PurAgioEntryCollection()
    {
        super(PurAgioEntryInfo.class);
    }
    public boolean add(PurAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public PurAgioEntryInfo get(int index)
    {
        return(PurAgioEntryInfo)getObject(index);
    }
    public PurAgioEntryInfo get(Object key)
    {
        return(PurAgioEntryInfo)getObject(key);
    }
    public void set(int index, PurAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}