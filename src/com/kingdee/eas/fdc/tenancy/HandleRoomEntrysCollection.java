package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HandleRoomEntrysCollection extends AbstractObjectCollection 
{
    public HandleRoomEntrysCollection()
    {
        super(HandleRoomEntrysInfo.class);
    }
    public boolean add(HandleRoomEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HandleRoomEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HandleRoomEntrysInfo item)
    {
        return removeObject(item);
    }
    public HandleRoomEntrysInfo get(int index)
    {
        return(HandleRoomEntrysInfo)getObject(index);
    }
    public HandleRoomEntrysInfo get(Object key)
    {
        return(HandleRoomEntrysInfo)getObject(key);
    }
    public void set(int index, HandleRoomEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HandleRoomEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HandleRoomEntrysInfo item)
    {
        return super.indexOf(item);
    }
}