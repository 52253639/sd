package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepConPayPlanContractCollection extends AbstractObjectCollection 
{
    public FDCProDepConPayPlanContractCollection()
    {
        super(FDCProDepConPayPlanContractInfo.class);
    }
    public boolean add(FDCProDepConPayPlanContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepConPayPlanContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepConPayPlanContractInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepConPayPlanContractInfo get(int index)
    {
        return(FDCProDepConPayPlanContractInfo)getObject(index);
    }
    public FDCProDepConPayPlanContractInfo get(Object key)
    {
        return(FDCProDepConPayPlanContractInfo)getObject(key);
    }
    public void set(int index, FDCProDepConPayPlanContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepConPayPlanContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepConPayPlanContractInfo item)
    {
        return super.indexOf(item);
    }
}