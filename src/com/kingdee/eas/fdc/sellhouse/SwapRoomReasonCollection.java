package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SwapRoomReasonCollection extends AbstractObjectCollection 
{
    public SwapRoomReasonCollection()
    {
        super(SwapRoomReasonInfo.class);
    }
    public boolean add(SwapRoomReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SwapRoomReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SwapRoomReasonInfo item)
    {
        return removeObject(item);
    }
    public SwapRoomReasonInfo get(int index)
    {
        return(SwapRoomReasonInfo)getObject(index);
    }
    public SwapRoomReasonInfo get(Object key)
    {
        return(SwapRoomReasonInfo)getObject(key);
    }
    public void set(int index, SwapRoomReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SwapRoomReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SwapRoomReasonInfo item)
    {
        return super.indexOf(item);
    }
}