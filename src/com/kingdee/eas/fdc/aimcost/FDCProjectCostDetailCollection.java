package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProjectCostDetailCollection extends AbstractObjectCollection 
{
    public FDCProjectCostDetailCollection()
    {
        super(FDCProjectCostDetailInfo.class);
    }
    public boolean add(FDCProjectCostDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProjectCostDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProjectCostDetailInfo item)
    {
        return removeObject(item);
    }
    public FDCProjectCostDetailInfo get(int index)
    {
        return(FDCProjectCostDetailInfo)getObject(index);
    }
    public FDCProjectCostDetailInfo get(Object key)
    {
        return(FDCProjectCostDetailInfo)getObject(key);
    }
    public void set(int index, FDCProjectCostDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProjectCostDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProjectCostDetailInfo item)
    {
        return super.indexOf(item);
    }
}