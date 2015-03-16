package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomAssistantCollection extends AbstractObjectCollection 
{
    public RoomAssistantCollection()
    {
        super(RoomAssistantInfo.class);
    }
    public boolean add(RoomAssistantInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomAssistantCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomAssistantInfo item)
    {
        return removeObject(item);
    }
    public RoomAssistantInfo get(int index)
    {
        return(RoomAssistantInfo)getObject(index);
    }
    public RoomAssistantInfo get(Object key)
    {
        return(RoomAssistantInfo)getObject(key);
    }
    public void set(int index, RoomAssistantInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomAssistantInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomAssistantInfo item)
    {
        return super.indexOf(item);
    }
}