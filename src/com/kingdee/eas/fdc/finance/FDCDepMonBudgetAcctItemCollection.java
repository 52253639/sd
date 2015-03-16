package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepMonBudgetAcctItemCollection extends AbstractObjectCollection 
{
    public FDCDepMonBudgetAcctItemCollection()
    {
        super(FDCDepMonBudgetAcctItemInfo.class);
    }
    public boolean add(FDCDepMonBudgetAcctItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepMonBudgetAcctItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepMonBudgetAcctItemInfo item)
    {
        return removeObject(item);
    }
    public FDCDepMonBudgetAcctItemInfo get(int index)
    {
        return(FDCDepMonBudgetAcctItemInfo)getObject(index);
    }
    public FDCDepMonBudgetAcctItemInfo get(Object key)
    {
        return(FDCDepMonBudgetAcctItemInfo)getObject(key);
    }
    public void set(int index, FDCDepMonBudgetAcctItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepMonBudgetAcctItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepMonBudgetAcctItemInfo item)
    {
        return super.indexOf(item);
    }
}