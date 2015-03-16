package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class KeepRoomDownCollection extends AbstractObjectCollection 
{
    public KeepRoomDownCollection()
    {
        super(KeepRoomDownInfo.class);
    }
    public boolean add(KeepRoomDownInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(KeepRoomDownCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(KeepRoomDownInfo item)
    {
        return removeObject(item);
    }
    public KeepRoomDownInfo get(int index)
    {
        return(KeepRoomDownInfo)getObject(index);
    }
    public KeepRoomDownInfo get(Object key)
    {
        return(KeepRoomDownInfo)getObject(key);
    }
    public void set(int index, KeepRoomDownInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(KeepRoomDownInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(KeepRoomDownInfo item)
    {
        return super.indexOf(item);
    }
}