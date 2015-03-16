package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepMonBudgetAcctCollection extends AbstractObjectCollection 
{
    public FDCDepMonBudgetAcctCollection()
    {
        super(FDCDepMonBudgetAcctInfo.class);
    }
    public boolean add(FDCDepMonBudgetAcctInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepMonBudgetAcctCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepMonBudgetAcctInfo item)
    {
        return removeObject(item);
    }
    public FDCDepMonBudgetAcctInfo get(int index)
    {
        return(FDCDepMonBudgetAcctInfo)getObject(index);
    }
    public FDCDepMonBudgetAcctInfo get(Object key)
    {
        return(FDCDepMonBudgetAcctInfo)getObject(key);
    }
    public void set(int index, FDCDepMonBudgetAcctInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepMonBudgetAcctInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepMonBudgetAcctInfo item)
    {
        return super.indexOf(item);
    }
}