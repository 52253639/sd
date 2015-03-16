package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SincerReceiveEntryCollection extends AbstractObjectCollection 
{
    public SincerReceiveEntryCollection()
    {
        super(SincerReceiveEntryInfo.class);
    }
    public boolean add(SincerReceiveEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SincerReceiveEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SincerReceiveEntryInfo item)
    {
        return removeObject(item);
    }
    public SincerReceiveEntryInfo get(int index)
    {
        return(SincerReceiveEntryInfo)getObject(index);
    }
    public SincerReceiveEntryInfo get(Object key)
    {
        return(SincerReceiveEntryInfo)getObject(key);
    }
    public void set(int index, SincerReceiveEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SincerReceiveEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SincerReceiveEntryInfo item)
    {
        return super.indexOf(item);
    }
}