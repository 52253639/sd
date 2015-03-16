package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgioRoomEntryCollection extends AbstractObjectCollection 
{
    public AgioRoomEntryCollection()
    {
        super(AgioRoomEntryInfo.class);
    }
    public boolean add(AgioRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgioRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgioRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public AgioRoomEntryInfo get(int index)
    {
        return(AgioRoomEntryInfo)getObject(index);
    }
    public AgioRoomEntryInfo get(Object key)
    {
        return(AgioRoomEntryInfo)getObject(key);
    }
    public void set(int index, AgioRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgioRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgioRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}