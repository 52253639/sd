package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HopedFloorCollection extends AbstractObjectCollection 
{
    public HopedFloorCollection()
    {
        super(HopedFloorInfo.class);
    }
    public boolean add(HopedFloorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HopedFloorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HopedFloorInfo item)
    {
        return removeObject(item);
    }
    public HopedFloorInfo get(int index)
    {
        return(HopedFloorInfo)getObject(index);
    }
    public HopedFloorInfo get(Object key)
    {
        return(HopedFloorInfo)getObject(key);
    }
    public void set(int index, HopedFloorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HopedFloorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HopedFloorInfo item)
    {
        return super.indexOf(item);
    }
}