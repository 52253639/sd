package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeRoomCollection extends AbstractObjectCollection 
{
    public ChangeRoomCollection()
    {
        super(ChangeRoomInfo.class);
    }
    public boolean add(ChangeRoomInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeRoomCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeRoomInfo item)
    {
        return removeObject(item);
    }
    public ChangeRoomInfo get(int index)
    {
        return(ChangeRoomInfo)getObject(index);
    }
    public ChangeRoomInfo get(Object key)
    {
        return(ChangeRoomInfo)getObject(key);
    }
    public void set(int index, ChangeRoomInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeRoomInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeRoomInfo item)
    {
        return super.indexOf(item);
    }
}