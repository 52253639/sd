package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RenameRoomReasonCollection extends AbstractObjectCollection 
{
    public RenameRoomReasonCollection()
    {
        super(RenameRoomReasonInfo.class);
    }
    public boolean add(RenameRoomReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RenameRoomReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RenameRoomReasonInfo item)
    {
        return removeObject(item);
    }
    public RenameRoomReasonInfo get(int index)
    {
        return(RenameRoomReasonInfo)getObject(index);
    }
    public RenameRoomReasonInfo get(Object key)
    {
        return(RenameRoomReasonInfo)getObject(key);
    }
    public void set(int index, RenameRoomReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RenameRoomReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RenameRoomReasonInfo item)
    {
        return super.indexOf(item);
    }
}