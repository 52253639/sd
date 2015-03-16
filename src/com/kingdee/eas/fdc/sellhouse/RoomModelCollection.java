package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomModelCollection extends AbstractObjectCollection 
{
    public RoomModelCollection()
    {
        super(RoomModelInfo.class);
    }
    public boolean add(RoomModelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomModelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomModelInfo item)
    {
        return removeObject(item);
    }
    public RoomModelInfo get(int index)
    {
        return(RoomModelInfo)getObject(index);
    }
    public RoomModelInfo get(Object key)
    {
        return(RoomModelInfo)getObject(key);
    }
    public void set(int index, RoomModelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomModelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomModelInfo item)
    {
        return super.indexOf(item);
    }
}