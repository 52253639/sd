package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DepConPayPlanSplitBillCollection extends AbstractObjectCollection 
{
    public DepConPayPlanSplitBillCollection()
    {
        super(DepConPayPlanSplitBillInfo.class);
    }
    public boolean add(DepConPayPlanSplitBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DepConPayPlanSplitBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DepConPayPlanSplitBillInfo item)
    {
        return removeObject(item);
    }
    public DepConPayPlanSplitBillInfo get(int index)
    {
        return(DepConPayPlanSplitBillInfo)getObject(index);
    }
    public DepConPayPlanSplitBillInfo get(Object key)
    {
        return(DepConPayPlanSplitBillInfo)getObject(key);
    }
    public void set(int index, DepConPayPlanSplitBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DepConPayPlanSplitBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DepConPayPlanSplitBillInfo item)
    {
        return super.indexOf(item);
    }
}