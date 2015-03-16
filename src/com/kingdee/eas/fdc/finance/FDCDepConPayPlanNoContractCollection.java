package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanNoContractCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanNoContractCollection()
    {
        super(FDCDepConPayPlanNoContractInfo.class);
    }
    public boolean add(FDCDepConPayPlanNoContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanNoContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanNoContractInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanNoContractInfo get(int index)
    {
        return(FDCDepConPayPlanNoContractInfo)getObject(index);
    }
    public FDCDepConPayPlanNoContractInfo get(Object key)
    {
        return(FDCDepConPayPlanNoContractInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanNoContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanNoContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanNoContractInfo item)
    {
        return super.indexOf(item);
    }
}