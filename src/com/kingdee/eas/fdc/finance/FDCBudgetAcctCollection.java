package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBudgetAcctCollection extends AbstractObjectCollection 
{
    public FDCBudgetAcctCollection()
    {
        super(FDCBudgetAcctInfo.class);
    }
    public boolean add(FDCBudgetAcctInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBudgetAcctCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBudgetAcctInfo item)
    {
        return removeObject(item);
    }
    public FDCBudgetAcctInfo get(int index)
    {
        return(FDCBudgetAcctInfo)getObject(index);
    }
    public FDCBudgetAcctInfo get(Object key)
    {
        return(FDCBudgetAcctInfo)getObject(key);
    }
    public void set(int index, FDCBudgetAcctInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBudgetAcctInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBudgetAcctInfo item)
    {
        return super.indexOf(item);
    }
}