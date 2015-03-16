package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShowDeductOfPartABillCollection extends AbstractObjectCollection 
{
    public ShowDeductOfPartABillCollection()
    {
        super(ShowDeductOfPartABillInfo.class);
    }
    public boolean add(ShowDeductOfPartABillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShowDeductOfPartABillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShowDeductOfPartABillInfo item)
    {
        return removeObject(item);
    }
    public ShowDeductOfPartABillInfo get(int index)
    {
        return(ShowDeductOfPartABillInfo)getObject(index);
    }
    public ShowDeductOfPartABillInfo get(Object key)
    {
        return(ShowDeductOfPartABillInfo)getObject(key);
    }
    public void set(int index, ShowDeductOfPartABillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShowDeductOfPartABillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShowDeductOfPartABillInfo item)
    {
        return super.indexOf(item);
    }
}