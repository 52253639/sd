package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCYearBudgetAcctItemCollection extends AbstractObjectCollection 
{
    public FDCYearBudgetAcctItemCollection()
    {
        super(FDCYearBudgetAcctItemInfo.class);
    }
    public boolean add(FDCYearBudgetAcctItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCYearBudgetAcctItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCYearBudgetAcctItemInfo item)
    {
        return removeObject(item);
    }
    public FDCYearBudgetAcctItemInfo get(int index)
    {
        return(FDCYearBudgetAcctItemInfo)getObject(index);
    }
    public FDCYearBudgetAcctItemInfo get(Object key)
    {
        return(FDCYearBudgetAcctItemInfo)getObject(key);
    }
    public void set(int index, FDCYearBudgetAcctItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCYearBudgetAcctItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCYearBudgetAcctItemInfo item)
    {
        return super.indexOf(item);
    }
}