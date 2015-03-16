package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomLoanAFMEntrysCollection extends AbstractObjectCollection 
{
    public RoomLoanAFMEntrysCollection()
    {
        super(RoomLoanAFMEntrysInfo.class);
    }
    public boolean add(RoomLoanAFMEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomLoanAFMEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomLoanAFMEntrysInfo item)
    {
        return removeObject(item);
    }
    public RoomLoanAFMEntrysInfo get(int index)
    {
        return(RoomLoanAFMEntrysInfo)getObject(index);
    }
    public RoomLoanAFMEntrysInfo get(Object key)
    {
        return(RoomLoanAFMEntrysInfo)getObject(key);
    }
    public void set(int index, RoomLoanAFMEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomLoanAFMEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomLoanAFMEntrysInfo item)
    {
        return super.indexOf(item);
    }
}