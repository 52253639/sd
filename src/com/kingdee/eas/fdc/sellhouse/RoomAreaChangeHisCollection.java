package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomAreaChangeHisCollection extends AbstractObjectCollection 
{
    public RoomAreaChangeHisCollection()
    {
        super(RoomAreaChangeHisInfo.class);
    }
    public boolean add(RoomAreaChangeHisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomAreaChangeHisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomAreaChangeHisInfo item)
    {
        return removeObject(item);
    }
    public RoomAreaChangeHisInfo get(int index)
    {
        return(RoomAreaChangeHisInfo)getObject(index);
    }
    public RoomAreaChangeHisInfo get(Object key)
    {
        return(RoomAreaChangeHisInfo)getObject(key);
    }
    public void set(int index, RoomAreaChangeHisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomAreaChangeHisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomAreaChangeHisInfo item)
    {
        return super.indexOf(item);
    }
}