package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomAreaCompensateCollection extends AbstractObjectCollection 
{
    public RoomAreaCompensateCollection()
    {
        super(RoomAreaCompensateInfo.class);
    }
    public boolean add(RoomAreaCompensateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomAreaCompensateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomAreaCompensateInfo item)
    {
        return removeObject(item);
    }
    public RoomAreaCompensateInfo get(int index)
    {
        return(RoomAreaCompensateInfo)getObject(index);
    }
    public RoomAreaCompensateInfo get(Object key)
    {
        return(RoomAreaCompensateInfo)getObject(key);
    }
    public void set(int index, RoomAreaCompensateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomAreaCompensateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomAreaCompensateInfo item)
    {
        return super.indexOf(item);
    }
}