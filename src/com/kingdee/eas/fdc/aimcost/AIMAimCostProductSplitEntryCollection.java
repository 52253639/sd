package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AIMAimCostProductSplitEntryCollection extends AbstractObjectCollection 
{
    public AIMAimCostProductSplitEntryCollection()
    {
        super(AIMAimCostProductSplitEntryInfo.class);
    }
    public boolean add(AIMAimCostProductSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AIMAimCostProductSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AIMAimCostProductSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public AIMAimCostProductSplitEntryInfo get(int index)
    {
        return(AIMAimCostProductSplitEntryInfo)getObject(index);
    }
    public AIMAimCostProductSplitEntryInfo get(Object key)
    {
        return(AIMAimCostProductSplitEntryInfo)getObject(key);
    }
    public void set(int index, AIMAimCostProductSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AIMAimCostProductSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AIMAimCostProductSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}