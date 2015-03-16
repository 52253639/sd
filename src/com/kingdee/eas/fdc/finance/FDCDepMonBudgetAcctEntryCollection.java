package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepMonBudgetAcctEntryCollection extends AbstractObjectCollection 
{
    public FDCDepMonBudgetAcctEntryCollection()
    {
        super(FDCDepMonBudgetAcctEntryInfo.class);
    }
    public boolean add(FDCDepMonBudgetAcctEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepMonBudgetAcctEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepMonBudgetAcctEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCDepMonBudgetAcctEntryInfo get(int index)
    {
        return(FDCDepMonBudgetAcctEntryInfo)getObject(index);
    }
    public FDCDepMonBudgetAcctEntryInfo get(Object key)
    {
        return(FDCDepMonBudgetAcctEntryInfo)getObject(key);
    }
    public void set(int index, FDCDepMonBudgetAcctEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepMonBudgetAcctEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepMonBudgetAcctEntryInfo item)
    {
        return super.indexOf(item);
    }
}