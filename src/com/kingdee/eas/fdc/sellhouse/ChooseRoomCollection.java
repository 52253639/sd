package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChooseRoomCollection extends AbstractObjectCollection 
{
    public ChooseRoomCollection()
    {
        super(ChooseRoomInfo.class);
    }
    public boolean add(ChooseRoomInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChooseRoomCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChooseRoomInfo item)
    {
        return removeObject(item);
    }
    public ChooseRoomInfo get(int index)
    {
        return(ChooseRoomInfo)getObject(index);
    }
    public ChooseRoomInfo get(Object key)
    {
        return(ChooseRoomInfo)getObject(key);
    }
    public void set(int index, ChooseRoomInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChooseRoomInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChooseRoomInfo item)
    {
        return super.indexOf(item);
    }
}