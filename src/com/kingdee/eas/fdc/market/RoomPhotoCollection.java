package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPhotoCollection extends AbstractObjectCollection 
{
    public RoomPhotoCollection()
    {
        super(RoomPhotoInfo.class);
    }
    public boolean add(RoomPhotoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPhotoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPhotoInfo item)
    {
        return removeObject(item);
    }
    public RoomPhotoInfo get(int index)
    {
        return(RoomPhotoInfo)getObject(index);
    }
    public RoomPhotoInfo get(Object key)
    {
        return(RoomPhotoInfo)getObject(key);
    }
    public void set(int index, RoomPhotoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPhotoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPhotoInfo item)
    {
        return super.indexOf(item);
    }
}