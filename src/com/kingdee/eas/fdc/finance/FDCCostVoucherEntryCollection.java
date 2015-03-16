package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCostVoucherEntryCollection extends AbstractObjectCollection 
{
    public FDCCostVoucherEntryCollection()
    {
        super(FDCCostVoucherEntryInfo.class);
    }
    public boolean add(FDCCostVoucherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCostVoucherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCostVoucherEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCCostVoucherEntryInfo get(int index)
    {
        return(FDCCostVoucherEntryInfo)getObject(index);
    }
    public FDCCostVoucherEntryInfo get(Object key)
    {
        return(FDCCostVoucherEntryInfo)getObject(key);
    }
    public void set(int index, FDCCostVoucherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCostVoucherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCostVoucherEntryInfo item)
    {
        return super.indexOf(item);
    }
}