package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBudgetAcctDataCollection extends AbstractObjectCollection 
{
    public FDCBudgetAcctDataCollection()
    {
        super(FDCBudgetAcctDataInfo.class);
    }
    public boolean add(FDCBudgetAcctDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBudgetAcctDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBudgetAcctDataInfo item)
    {
        return removeObject(item);
    }
    public FDCBudgetAcctDataInfo get(int index)
    {
        return(FDCBudgetAcctDataInfo)getObject(index);
    }
    public FDCBudgetAcctDataInfo get(Object key)
    {
        return(FDCBudgetAcctDataInfo)getObject(key);
    }
    public void set(int index, FDCBudgetAcctDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBudgetAcctDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBudgetAcctDataInfo item)
    {
        return super.indexOf(item);
    }
}