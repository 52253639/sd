package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomFormCollection extends AbstractObjectCollection 
{
    public RoomFormCollection()
    {
        super(RoomFormInfo.class);
    }
    public boolean add(RoomFormInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomFormCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomFormInfo item)
    {
        return removeObject(item);
    }
    public RoomFormInfo get(int index)
    {
        return(RoomFormInfo)getObject(index);
    }
    public RoomFormInfo get(Object key)
    {
        return(RoomFormInfo)getObject(key);
    }
    public void set(int index, RoomFormInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomFormInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomFormInfo item)
    {
        return super.indexOf(item);
    }
}