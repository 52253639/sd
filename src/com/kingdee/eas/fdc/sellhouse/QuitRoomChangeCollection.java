package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuitRoomChangeCollection extends AbstractObjectCollection 
{
    public QuitRoomChangeCollection()
    {
        super(QuitRoomChangeInfo.class);
    }
    public boolean add(QuitRoomChangeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuitRoomChangeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuitRoomChangeInfo item)
    {
        return removeObject(item);
    }
    public QuitRoomChangeInfo get(int index)
    {
        return(QuitRoomChangeInfo)getObject(index);
    }
    public QuitRoomChangeInfo get(Object key)
    {
        return(QuitRoomChangeInfo)getObject(key);
    }
    public void set(int index, QuitRoomChangeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuitRoomChangeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuitRoomChangeInfo item)
    {
        return super.indexOf(item);
    }
}