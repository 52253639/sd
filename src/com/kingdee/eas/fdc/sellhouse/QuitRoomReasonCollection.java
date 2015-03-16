package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuitRoomReasonCollection extends AbstractObjectCollection 
{
    public QuitRoomReasonCollection()
    {
        super(QuitRoomReasonInfo.class);
    }
    public boolean add(QuitRoomReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuitRoomReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuitRoomReasonInfo item)
    {
        return removeObject(item);
    }
    public QuitRoomReasonInfo get(int index)
    {
        return(QuitRoomReasonInfo)getObject(index);
    }
    public QuitRoomReasonInfo get(Object key)
    {
        return(QuitRoomReasonInfo)getObject(key);
    }
    public void set(int index, QuitRoomReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuitRoomReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuitRoomReasonInfo item)
    {
        return super.indexOf(item);
    }
}