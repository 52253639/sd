package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCYearBudgetAcctCollection extends AbstractObjectCollection 
{
    public FDCYearBudgetAcctCollection()
    {
        super(FDCYearBudgetAcctInfo.class);
    }
    public boolean add(FDCYearBudgetAcctInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCYearBudgetAcctCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCYearBudgetAcctInfo item)
    {
        return removeObject(item);
    }
    public FDCYearBudgetAcctInfo get(int index)
    {
        return(FDCYearBudgetAcctInfo)getObject(index);
    }
    public FDCYearBudgetAcctInfo get(Object key)
    {
        return(FDCYearBudgetAcctInfo)getObject(key);
    }
    public void set(int index, FDCYearBudgetAcctInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCYearBudgetAcctInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCYearBudgetAcctInfo item)
    {
        return super.indexOf(item);
    }
}