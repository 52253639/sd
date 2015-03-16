package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBaseVoucherEntryCollection extends AbstractObjectCollection 
{
    public FDCBaseVoucherEntryCollection()
    {
        super(FDCBaseVoucherEntryInfo.class);
    }
    public boolean add(FDCBaseVoucherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBaseVoucherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBaseVoucherEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCBaseVoucherEntryInfo get(int index)
    {
        return(FDCBaseVoucherEntryInfo)getObject(index);
    }
    public FDCBaseVoucherEntryInfo get(Object key)
    {
        return(FDCBaseVoucherEntryInfo)getObject(key);
    }
    public void set(int index, FDCBaseVoucherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBaseVoucherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBaseVoucherEntryInfo item)
    {
        return super.indexOf(item);
    }
}