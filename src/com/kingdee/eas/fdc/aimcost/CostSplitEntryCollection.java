package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostSplitEntryCollection extends AbstractObjectCollection 
{
    public CostSplitEntryCollection()
    {
        super(CostSplitEntryInfo.class);
    }
    public boolean add(CostSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public CostSplitEntryInfo get(int index)
    {
        return(CostSplitEntryInfo)getObject(index);
    }
    public CostSplitEntryInfo get(Object key)
    {
        return(CostSplitEntryInfo)getObject(key);
    }
    public void set(int index, CostSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}