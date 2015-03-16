package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCMonthBudgetAcctItemCollection extends AbstractObjectCollection 
{
    public FDCMonthBudgetAcctItemCollection()
    {
        super(FDCMonthBudgetAcctItemInfo.class);
    }
    public boolean add(FDCMonthBudgetAcctItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCMonthBudgetAcctItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCMonthBudgetAcctItemInfo item)
    {
        return removeObject(item);
    }
    public FDCMonthBudgetAcctItemInfo get(int index)
    {
        return(FDCMonthBudgetAcctItemInfo)getObject(index);
    }
    public FDCMonthBudgetAcctItemInfo get(Object key)
    {
        return(FDCMonthBudgetAcctItemInfo)getObject(key);
    }
    public void set(int index, FDCMonthBudgetAcctItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCMonthBudgetAcctItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCMonthBudgetAcctItemInfo item)
    {
        return super.indexOf(item);
    }
}