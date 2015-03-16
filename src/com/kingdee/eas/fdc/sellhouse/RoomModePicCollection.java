package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomModePicCollection extends AbstractObjectCollection 
{
    public RoomModePicCollection()
    {
        super(RoomModePicInfo.class);
    }
    public boolean add(RoomModePicInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomModePicCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomModePicInfo item)
    {
        return removeObject(item);
    }
    public RoomModePicInfo get(int index)
    {
        return(RoomModePicInfo)getObject(index);
    }
    public RoomModePicInfo get(Object key)
    {
        return(RoomModePicInfo)getObject(key);
    }
    public void set(int index, RoomModePicInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomModePicInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomModePicInfo item)
    {
        return super.indexOf(item);
    }
}