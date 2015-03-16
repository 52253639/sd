package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DepConPayPlanSplitEntryCollection extends AbstractObjectCollection 
{
    public DepConPayPlanSplitEntryCollection()
    {
        super(DepConPayPlanSplitEntryInfo.class);
    }
    public boolean add(DepConPayPlanSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DepConPayPlanSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DepConPayPlanSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public DepConPayPlanSplitEntryInfo get(int index)
    {
        return(DepConPayPlanSplitEntryInfo)getObject(index);
    }
    public DepConPayPlanSplitEntryInfo get(Object key)
    {
        return(DepConPayPlanSplitEntryInfo)getObject(key);
    }
    public void set(int index, DepConPayPlanSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DepConPayPlanSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DepConPayPlanSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}