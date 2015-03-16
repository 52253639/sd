package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyBatchMaterialsCollection extends AbstractObjectCollection 
{
    public RoomPropertyBatchMaterialsCollection()
    {
        super(RoomPropertyBatchMaterialsInfo.class);
    }
    public boolean add(RoomPropertyBatchMaterialsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyBatchMaterialsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyBatchMaterialsInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyBatchMaterialsInfo get(int index)
    {
        return(RoomPropertyBatchMaterialsInfo)getObject(index);
    }
    public RoomPropertyBatchMaterialsInfo get(Object key)
    {
        return(RoomPropertyBatchMaterialsInfo)getObject(key);
    }
    public void set(int index, RoomPropertyBatchMaterialsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyBatchMaterialsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyBatchMaterialsInfo item)
    {
        return super.indexOf(item);
    }
}