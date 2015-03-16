package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyBatchStepCollection extends AbstractObjectCollection 
{
    public RoomPropertyBatchStepCollection()
    {
        super(RoomPropertyBatchStepInfo.class);
    }
    public boolean add(RoomPropertyBatchStepInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyBatchStepCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyBatchStepInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyBatchStepInfo get(int index)
    {
        return(RoomPropertyBatchStepInfo)getObject(index);
    }
    public RoomPropertyBatchStepInfo get(Object key)
    {
        return(RoomPropertyBatchStepInfo)getObject(key);
    }
    public void set(int index, RoomPropertyBatchStepInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyBatchStepInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyBatchStepInfo item)
    {
        return super.indexOf(item);
    }
}