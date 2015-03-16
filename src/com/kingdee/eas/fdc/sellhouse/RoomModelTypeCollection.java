package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomModelTypeCollection extends AbstractObjectCollection 
{
    public RoomModelTypeCollection()
    {
        super(RoomModelTypeInfo.class);
    }
    public boolean add(RoomModelTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomModelTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomModelTypeInfo item)
    {
        return removeObject(item);
    }
    public RoomModelTypeInfo get(int index)
    {
        return(RoomModelTypeInfo)getObject(index);
    }
    public RoomModelTypeInfo get(Object key)
    {
        return(RoomModelTypeInfo)getObject(key);
    }
    public void set(int index, RoomModelTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomModelTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomModelTypeInfo item)
    {
        return super.indexOf(item);
    }
}