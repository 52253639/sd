package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanSplitCollection extends AbstractObjectCollection 
{
    public ConPayPlanSplitCollection()
    {
        super(ConPayPlanSplitInfo.class);
    }
    public boolean add(ConPayPlanSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanSplitInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanSplitInfo get(int index)
    {
        return(ConPayPlanSplitInfo)getObject(index);
    }
    public ConPayPlanSplitInfo get(Object key)
    {
        return(ConPayPlanSplitInfo)getObject(key);
    }
    public void set(int index, ConPayPlanSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanSplitInfo item)
    {
        return super.indexOf(item);
    }
}