package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanBillCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanBillCollection()
    {
        super(FDCDepConPayPlanBillInfo.class);
    }
    public boolean add(FDCDepConPayPlanBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanBillInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanBillInfo get(int index)
    {
        return(FDCDepConPayPlanBillInfo)getObject(index);
    }
    public FDCDepConPayPlanBillInfo get(Object key)
    {
        return(FDCDepConPayPlanBillInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanBillInfo item)
    {
        return super.indexOf(item);
    }
}