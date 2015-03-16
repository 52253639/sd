package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCPayVoucherEntryCollection extends AbstractObjectCollection 
{
    public FDCPayVoucherEntryCollection()
    {
        super(FDCPayVoucherEntryInfo.class);
    }
    public boolean add(FDCPayVoucherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCPayVoucherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCPayVoucherEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCPayVoucherEntryInfo get(int index)
    {
        return(FDCPayVoucherEntryInfo)getObject(index);
    }
    public FDCPayVoucherEntryInfo get(Object key)
    {
        return(FDCPayVoucherEntryInfo)getObject(key);
    }
    public void set(int index, FDCPayVoucherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCPayVoucherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCPayVoucherEntryInfo item)
    {
        return super.indexOf(item);
    }
}