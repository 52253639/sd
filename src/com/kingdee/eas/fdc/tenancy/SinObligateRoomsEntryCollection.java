package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SinObligateRoomsEntryCollection extends AbstractObjectCollection 
{
    public SinObligateRoomsEntryCollection()
    {
        super(SinObligateRoomsEntryInfo.class);
    }
    public boolean add(SinObligateRoomsEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SinObligateRoomsEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SinObligateRoomsEntryInfo item)
    {
        return removeObject(item);
    }
    public SinObligateRoomsEntryInfo get(int index)
    {
        return(SinObligateRoomsEntryInfo)getObject(index);
    }
    public SinObligateRoomsEntryInfo get(Object key)
    {
        return(SinObligateRoomsEntryInfo)getObject(key);
    }
    public void set(int index, SinObligateRoomsEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SinObligateRoomsEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SinObligateRoomsEntryInfo item)
    {
        return super.indexOf(item);
    }
}