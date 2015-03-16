package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeductBillCollection extends AbstractObjectCollection 
{
    public DeductBillCollection()
    {
        super(DeductBillInfo.class);
    }
    public boolean add(DeductBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeductBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeductBillInfo item)
    {
        return removeObject(item);
    }
    public DeductBillInfo get(int index)
    {
        return(DeductBillInfo)getObject(index);
    }
    public DeductBillInfo get(Object key)
    {
        return(DeductBillInfo)getObject(key);
    }
    public void set(int index, DeductBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeductBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeductBillInfo item)
    {
        return super.indexOf(item);
    }
}