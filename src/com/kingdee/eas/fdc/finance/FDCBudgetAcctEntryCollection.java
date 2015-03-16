package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBudgetAcctEntryCollection extends AbstractObjectCollection 
{
    public FDCBudgetAcctEntryCollection()
    {
        super(FDCBudgetAcctEntryInfo.class);
    }
    public boolean add(FDCBudgetAcctEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBudgetAcctEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBudgetAcctEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCBudgetAcctEntryInfo get(int index)
    {
        return(FDCBudgetAcctEntryInfo)getObject(index);
    }
    public FDCBudgetAcctEntryInfo get(Object key)
    {
        return(FDCBudgetAcctEntryInfo)getObject(key);
    }
    public void set(int index, FDCBudgetAcctEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBudgetAcctEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBudgetAcctEntryInfo item)
    {
        return super.indexOf(item);
    }
}