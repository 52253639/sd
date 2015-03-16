package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCMonthBudgetAcctEntryCollection extends AbstractObjectCollection 
{
    public FDCMonthBudgetAcctEntryCollection()
    {
        super(FDCMonthBudgetAcctEntryInfo.class);
    }
    public boolean add(FDCMonthBudgetAcctEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCMonthBudgetAcctEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCMonthBudgetAcctEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCMonthBudgetAcctEntryInfo get(int index)
    {
        return(FDCMonthBudgetAcctEntryInfo)getObject(index);
    }
    public FDCMonthBudgetAcctEntryInfo get(Object key)
    {
        return(FDCMonthBudgetAcctEntryInfo)getObject(key);
    }
    public void set(int index, FDCMonthBudgetAcctEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCMonthBudgetAcctEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCMonthBudgetAcctEntryInfo item)
    {
        return super.indexOf(item);
    }
}