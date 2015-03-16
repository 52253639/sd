package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RentAdjustEntrysCollection extends AbstractObjectCollection 
{
    public RentAdjustEntrysCollection()
    {
        super(RentAdjustEntrysInfo.class);
    }
    public boolean add(RentAdjustEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RentAdjustEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RentAdjustEntrysInfo item)
    {
        return removeObject(item);
    }
    public RentAdjustEntrysInfo get(int index)
    {
        return(RentAdjustEntrysInfo)getObject(index);
    }
    public RentAdjustEntrysInfo get(Object key)
    {
        return(RentAdjustEntrysInfo)getObject(key);
    }
    public void set(int index, RentAdjustEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RentAdjustEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RentAdjustEntrysInfo item)
    {
        return super.indexOf(item);
    }
}