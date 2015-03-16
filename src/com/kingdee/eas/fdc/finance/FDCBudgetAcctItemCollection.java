package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCBudgetAcctItemCollection extends AbstractObjectCollection 
{
    public FDCBudgetAcctItemCollection()
    {
        super(FDCBudgetAcctItemInfo.class);
    }
    public boolean add(FDCBudgetAcctItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCBudgetAcctItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCBudgetAcctItemInfo item)
    {
        return removeObject(item);
    }
    public FDCBudgetAcctItemInfo get(int index)
    {
        return(FDCBudgetAcctItemInfo)getObject(index);
    }
    public FDCBudgetAcctItemInfo get(Object key)
    {
        return(FDCBudgetAcctItemInfo)getObject(key);
    }
    public void set(int index, FDCBudgetAcctItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCBudgetAcctItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCBudgetAcctItemInfo item)
    {
        return super.indexOf(item);
    }
}