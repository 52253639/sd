package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepConPayPlanCollection extends AbstractObjectCollection 
{
    public FDCProDepConPayPlanCollection()
    {
        super(FDCProDepConPayPlanInfo.class);
    }
    public boolean add(FDCProDepConPayPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepConPayPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepConPayPlanInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepConPayPlanInfo get(int index)
    {
        return(FDCProDepConPayPlanInfo)getObject(index);
    }
    public FDCProDepConPayPlanInfo get(Object key)
    {
        return(FDCProDepConPayPlanInfo)getObject(key);
    }
    public void set(int index, FDCProDepConPayPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepConPayPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepConPayPlanInfo item)
    {
        return super.indexOf(item);
    }
}