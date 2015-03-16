package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettForPayVoucherEntryCollection extends AbstractObjectCollection 
{
    public SettForPayVoucherEntryCollection()
    {
        super(SettForPayVoucherEntryInfo.class);
    }
    public boolean add(SettForPayVoucherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettForPayVoucherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettForPayVoucherEntryInfo item)
    {
        return removeObject(item);
    }
    public SettForPayVoucherEntryInfo get(int index)
    {
        return(SettForPayVoucherEntryInfo)getObject(index);
    }
    public SettForPayVoucherEntryInfo get(Object key)
    {
        return(SettForPayVoucherEntryInfo)getObject(key);
    }
    public void set(int index, SettForPayVoucherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettForPayVoucherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettForPayVoucherEntryInfo item)
    {
        return super.indexOf(item);
    }
}