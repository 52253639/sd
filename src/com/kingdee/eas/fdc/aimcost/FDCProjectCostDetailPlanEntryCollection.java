package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProjectCostDetailPlanEntryCollection extends AbstractObjectCollection 
{
    public FDCProjectCostDetailPlanEntryCollection()
    {
        super(FDCProjectCostDetailPlanEntryInfo.class);
    }
    public boolean add(FDCProjectCostDetailPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProjectCostDetailPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProjectCostDetailPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProjectCostDetailPlanEntryInfo get(int index)
    {
        return(FDCProjectCostDetailPlanEntryInfo)getObject(index);
    }
    public FDCProjectCostDetailPlanEntryInfo get(Object key)
    {
        return(FDCProjectCostDetailPlanEntryInfo)getObject(key);
    }
    public void set(int index, FDCProjectCostDetailPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProjectCostDetailPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProjectCostDetailPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}