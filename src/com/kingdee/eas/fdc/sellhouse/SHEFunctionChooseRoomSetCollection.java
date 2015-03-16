package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEFunctionChooseRoomSetCollection extends AbstractObjectCollection 
{
    public SHEFunctionChooseRoomSetCollection()
    {
        super(SHEFunctionChooseRoomSetInfo.class);
    }
    public boolean add(SHEFunctionChooseRoomSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEFunctionChooseRoomSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEFunctionChooseRoomSetInfo item)
    {
        return removeObject(item);
    }
    public SHEFunctionChooseRoomSetInfo get(int index)
    {
        return(SHEFunctionChooseRoomSetInfo)getObject(index);
    }
    public SHEFunctionChooseRoomSetInfo get(Object key)
    {
        return(SHEFunctionChooseRoomSetInfo)getObject(key);
    }
    public void set(int index, SHEFunctionChooseRoomSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEFunctionChooseRoomSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEFunctionChooseRoomSetInfo item)
    {
        return super.indexOf(item);
    }
}