package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FIProductSettleBillCollection extends AbstractObjectCollection 
{
    public FIProductSettleBillCollection()
    {
        super(FIProductSettleBillInfo.class);
    }
    public boolean add(FIProductSettleBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FIProductSettleBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FIProductSettleBillInfo item)
    {
        return removeObject(item);
    }
    public FIProductSettleBillInfo get(int index)
    {
        return(FIProductSettleBillInfo)getObject(index);
    }
    public FIProductSettleBillInfo get(Object key)
    {
        return(FIProductSettleBillInfo)getObject(key);
    }
    public void set(int index, FIProductSettleBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FIProductSettleBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FIProductSettleBillInfo item)
    {
        return super.indexOf(item);
    }
}