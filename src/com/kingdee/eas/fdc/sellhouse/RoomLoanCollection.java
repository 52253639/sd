package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomLoanCollection extends AbstractObjectCollection 
{
    public RoomLoanCollection()
    {
        super(RoomLoanInfo.class);
    }
    public boolean add(RoomLoanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomLoanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomLoanInfo item)
    {
        return removeObject(item);
    }
    public RoomLoanInfo get(int index)
    {
        return(RoomLoanInfo)getObject(index);
    }
    public RoomLoanInfo get(Object key)
    {
        return(RoomLoanInfo)getObject(key);
    }
    public void set(int index, RoomLoanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomLoanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomLoanInfo item)
    {
        return super.indexOf(item);
    }
}