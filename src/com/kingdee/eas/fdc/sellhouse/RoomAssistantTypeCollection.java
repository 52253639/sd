package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomAssistantTypeCollection extends AbstractObjectCollection 
{
    public RoomAssistantTypeCollection()
    {
        super(RoomAssistantTypeInfo.class);
    }
    public boolean add(RoomAssistantTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomAssistantTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomAssistantTypeInfo item)
    {
        return removeObject(item);
    }
    public RoomAssistantTypeInfo get(int index)
    {
        return(RoomAssistantTypeInfo)getObject(index);
    }
    public RoomAssistantTypeInfo get(Object key)
    {
        return(RoomAssistantTypeInfo)getObject(key);
    }
    public void set(int index, RoomAssistantTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomAssistantTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomAssistantTypeInfo item)
    {
        return super.indexOf(item);
    }
}