package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCostChangeSettEntryCollection extends AbstractObjectCollection 
{
    public FDCCostChangeSettEntryCollection()
    {
        super(FDCCostChangeSettEntryInfo.class);
    }
    public boolean add(FDCCostChangeSettEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCostChangeSettEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCostChangeSettEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCCostChangeSettEntryInfo get(int index)
    {
        return(FDCCostChangeSettEntryInfo)getObject(index);
    }
    public FDCCostChangeSettEntryInfo get(Object key)
    {
        return(FDCCostChangeSettEntryInfo)getObject(key);
    }
    public void set(int index, FDCCostChangeSettEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCostChangeSettEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCostChangeSettEntryInfo item)
    {
        return super.indexOf(item);
    }
}