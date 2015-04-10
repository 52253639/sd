package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RentFreeBillCollection extends AbstractObjectCollection 
{
    public RentFreeBillCollection()
    {
        super(RentFreeBillInfo.class);
    }
    public boolean add(RentFreeBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RentFreeBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RentFreeBillInfo item)
    {
        return removeObject(item);
    }
    public RentFreeBillInfo get(int index)
    {
        return(RentFreeBillInfo)getObject(index);
    }
    public RentFreeBillInfo get(Object key)
    {
        return(RentFreeBillInfo)getObject(key);
    }
    public void set(int index, RentFreeBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RentFreeBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RentFreeBillInfo item)
    {
        return super.indexOf(item);
    }
}