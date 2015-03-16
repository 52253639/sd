package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostSplitCollection extends AbstractObjectCollection 
{
    public CostSplitCollection()
    {
        super(CostSplitInfo.class);
    }
    public boolean add(CostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostSplitInfo item)
    {
        return removeObject(item);
    }
    public CostSplitInfo get(int index)
    {
        return(CostSplitInfo)getObject(index);
    }
    public CostSplitInfo get(Object key)
    {
        return(CostSplitInfo)getObject(key);
    }
    public void set(int index, CostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostSplitInfo item)
    {
        return super.indexOf(item);
    }
}