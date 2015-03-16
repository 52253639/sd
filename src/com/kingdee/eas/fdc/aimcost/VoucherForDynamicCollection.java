package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class VoucherForDynamicCollection extends AbstractObjectCollection 
{
    public VoucherForDynamicCollection()
    {
        super(VoucherForDynamicInfo.class);
    }
    public boolean add(VoucherForDynamicInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(VoucherForDynamicCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(VoucherForDynamicInfo item)
    {
        return removeObject(item);
    }
    public VoucherForDynamicInfo get(int index)
    {
        return(VoucherForDynamicInfo)getObject(index);
    }
    public VoucherForDynamicInfo get(Object key)
    {
        return(VoucherForDynamicInfo)getObject(key);
    }
    public void set(int index, VoucherForDynamicInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(VoucherForDynamicInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(VoucherForDynamicInfo item)
    {
        return super.indexOf(item);
    }
}