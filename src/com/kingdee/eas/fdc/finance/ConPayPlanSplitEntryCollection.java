package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanSplitEntryCollection extends AbstractObjectCollection 
{
    public ConPayPlanSplitEntryCollection()
    {
        super(ConPayPlanSplitEntryInfo.class);
    }
    public boolean add(ConPayPlanSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanSplitEntryInfo get(int index)
    {
        return(ConPayPlanSplitEntryInfo)getObject(index);
    }
    public ConPayPlanSplitEntryInfo get(Object key)
    {
        return(ConPayPlanSplitEntryInfo)getObject(key);
    }
    public void set(int index, ConPayPlanSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}