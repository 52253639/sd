package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DepConPayPlanSplitItemCollection extends AbstractObjectCollection 
{
    public DepConPayPlanSplitItemCollection()
    {
        super(DepConPayPlanSplitItemInfo.class);
    }
    public boolean add(DepConPayPlanSplitItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DepConPayPlanSplitItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DepConPayPlanSplitItemInfo item)
    {
        return removeObject(item);
    }
    public DepConPayPlanSplitItemInfo get(int index)
    {
        return(DepConPayPlanSplitItemInfo)getObject(index);
    }
    public DepConPayPlanSplitItemInfo get(Object key)
    {
        return(DepConPayPlanSplitItemInfo)getObject(key);
    }
    public void set(int index, DepConPayPlanSplitItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DepConPayPlanSplitItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DepConPayPlanSplitItemInfo item)
    {
        return super.indexOf(item);
    }
}