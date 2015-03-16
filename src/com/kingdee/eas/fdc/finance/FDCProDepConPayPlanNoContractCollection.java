package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepConPayPlanNoContractCollection extends AbstractObjectCollection 
{
    public FDCProDepConPayPlanNoContractCollection()
    {
        super(FDCProDepConPayPlanNoContractInfo.class);
    }
    public boolean add(FDCProDepConPayPlanNoContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepConPayPlanNoContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepConPayPlanNoContractInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepConPayPlanNoContractInfo get(int index)
    {
        return(FDCProDepConPayPlanNoContractInfo)getObject(index);
    }
    public FDCProDepConPayPlanNoContractInfo get(Object key)
    {
        return(FDCProDepConPayPlanNoContractInfo)getObject(key);
    }
    public void set(int index, FDCProDepConPayPlanNoContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepConPayPlanNoContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepConPayPlanNoContractInfo item)
    {
        return super.indexOf(item);
    }
}