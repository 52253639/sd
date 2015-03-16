package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepConPayPlanUnsettledCollection extends AbstractObjectCollection 
{
    public FDCProDepConPayPlanUnsettledCollection()
    {
        super(FDCProDepConPayPlanUnsettledInfo.class);
    }
    public boolean add(FDCProDepConPayPlanUnsettledInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepConPayPlanUnsettledCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepConPayPlanUnsettledInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepConPayPlanUnsettledInfo get(int index)
    {
        return(FDCProDepConPayPlanUnsettledInfo)getObject(index);
    }
    public FDCProDepConPayPlanUnsettledInfo get(Object key)
    {
        return(FDCProDepConPayPlanUnsettledInfo)getObject(key);
    }
    public void set(int index, FDCProDepConPayPlanUnsettledInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepConPayPlanUnsettledInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepConPayPlanUnsettledInfo item)
    {
        return super.indexOf(item);
    }
}