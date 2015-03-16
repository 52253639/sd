package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuitRoomCollection extends AbstractObjectCollection 
{
    public QuitRoomCollection()
    {
        super(QuitRoomInfo.class);
    }
    public boolean add(QuitRoomInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuitRoomCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuitRoomInfo item)
    {
        return removeObject(item);
    }
    public QuitRoomInfo get(int index)
    {
        return(QuitRoomInfo)getObject(index);
    }
    public QuitRoomInfo get(Object key)
    {
        return(QuitRoomInfo)getObject(key);
    }
    public void set(int index, QuitRoomInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuitRoomInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuitRoomInfo item)
    {
        return super.indexOf(item);
    }
}