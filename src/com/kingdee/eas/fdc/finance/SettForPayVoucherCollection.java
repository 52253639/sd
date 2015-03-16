package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettForPayVoucherCollection extends AbstractObjectCollection 
{
    public SettForPayVoucherCollection()
    {
        super(SettForPayVoucherInfo.class);
    }
    public boolean add(SettForPayVoucherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettForPayVoucherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettForPayVoucherInfo item)
    {
        return removeObject(item);
    }
    public SettForPayVoucherInfo get(int index)
    {
        return(SettForPayVoucherInfo)getObject(index);
    }
    public SettForPayVoucherInfo get(Object key)
    {
        return(SettForPayVoucherInfo)getObject(key);
    }
    public void set(int index, SettForPayVoucherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettForPayVoucherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettForPayVoucherInfo item)
    {
        return super.indexOf(item);
    }
}