package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommerceRoomEntryCollection extends AbstractObjectCollection 
{
    public CommerceRoomEntryCollection()
    {
        super(CommerceRoomEntryInfo.class);
    }
    public boolean add(CommerceRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommerceRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommerceRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public CommerceRoomEntryInfo get(int index)
    {
        return(CommerceRoomEntryInfo)getObject(index);
    }
    public CommerceRoomEntryInfo get(Object key)
    {
        return(CommerceRoomEntryInfo)getObject(key);
    }
    public void set(int index, CommerceRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommerceRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommerceRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}