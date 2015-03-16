package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCMonthBudgetAcctCollection extends AbstractObjectCollection 
{
    public FDCMonthBudgetAcctCollection()
    {
        super(FDCMonthBudgetAcctInfo.class);
    }
    public boolean add(FDCMonthBudgetAcctInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCMonthBudgetAcctCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCMonthBudgetAcctInfo item)
    {
        return removeObject(item);
    }
    public FDCMonthBudgetAcctInfo get(int index)
    {
        return(FDCMonthBudgetAcctInfo)getObject(index);
    }
    public FDCMonthBudgetAcctInfo get(Object key)
    {
        return(FDCMonthBudgetAcctInfo)getObject(key);
    }
    public void set(int index, FDCMonthBudgetAcctInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCMonthBudgetAcctInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCMonthBudgetAcctInfo item)
    {
        return super.indexOf(item);
    }
}