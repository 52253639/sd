package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBudgetPeriodCollection extends AbstractObjectCollection 
{
    public FDCBudgetPeriodCollection()
    {
        super(FDCBudgetPeriodInfo.class);
    }
    public boolean add(FDCBudgetPeriodInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBudgetPeriodCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBudgetPeriodInfo item)
    {
        return removeObject(item);
    }
    public FDCBudgetPeriodInfo get(int index)
    {
        return(FDCBudgetPeriodInfo)getObject(index);
    }
    public FDCBudgetPeriodInfo get(Object key)
    {
        return(FDCBudgetPeriodInfo)getObject(key);
    }
    public void set(int index, FDCBudgetPeriodInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBudgetPeriodInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBudgetPeriodInfo item)
    {
        return super.indexOf(item);
    }
}