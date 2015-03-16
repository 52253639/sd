package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCYearBudgetAcctEntryCollection extends AbstractObjectCollection 
{
    public FDCYearBudgetAcctEntryCollection()
    {
        super(FDCYearBudgetAcctEntryInfo.class);
    }
    public boolean add(FDCYearBudgetAcctEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCYearBudgetAcctEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCYearBudgetAcctEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCYearBudgetAcctEntryInfo get(int index)
    {
        return(FDCYearBudgetAcctEntryInfo)getObject(index);
    }
    public FDCYearBudgetAcctEntryInfo get(Object key)
    {
        return(FDCYearBudgetAcctEntryInfo)getObject(key);
    }
    public void set(int index, FDCYearBudgetAcctEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCYearBudgetAcctEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCYearBudgetAcctEntryInfo item)
    {
        return super.indexOf(item);
    }
}