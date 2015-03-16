package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCostLogChangeSettEntryCollection extends AbstractObjectCollection 
{
    public FDCCostLogChangeSettEntryCollection()
    {
        super(FDCCostLogChangeSettEntryInfo.class);
    }
    public boolean add(FDCCostLogChangeSettEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCostLogChangeSettEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCostLogChangeSettEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCCostLogChangeSettEntryInfo get(int index)
    {
        return(FDCCostLogChangeSettEntryInfo)getObject(index);
    }
    public FDCCostLogChangeSettEntryInfo get(Object key)
    {
        return(FDCCostLogChangeSettEntryInfo)getObject(key);
    }
    public void set(int index, FDCCostLogChangeSettEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCostLogChangeSettEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCostLogChangeSettEntryInfo item)
    {
        return super.indexOf(item);
    }
}