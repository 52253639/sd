package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomSignContractCollection extends AbstractObjectCollection 
{
    public RoomSignContractCollection()
    {
        super(RoomSignContractInfo.class);
    }
    public boolean add(RoomSignContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomSignContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomSignContractInfo item)
    {
        return removeObject(item);
    }
    public RoomSignContractInfo get(int index)
    {
        return(RoomSignContractInfo)getObject(index);
    }
    public RoomSignContractInfo get(Object key)
    {
        return(RoomSignContractInfo)getObject(key);
    }
    public void set(int index, RoomSignContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomSignContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomSignContractInfo item)
    {
        return super.indexOf(item);
    }
}