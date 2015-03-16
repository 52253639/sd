package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanUnsettledConCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanUnsettledConCollection()
    {
        super(FDCDepConPayPlanUnsettledConInfo.class);
    }
    public boolean add(FDCDepConPayPlanUnsettledConInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanUnsettledConCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanUnsettledConInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanUnsettledConInfo get(int index)
    {
        return(FDCDepConPayPlanUnsettledConInfo)getObject(index);
    }
    public FDCDepConPayPlanUnsettledConInfo get(Object key)
    {
        return(FDCDepConPayPlanUnsettledConInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanUnsettledConInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanUnsettledConInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanUnsettledConInfo item)
    {
        return super.indexOf(item);
    }
}