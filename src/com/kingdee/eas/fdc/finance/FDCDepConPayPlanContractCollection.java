package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanContractCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanContractCollection()
    {
        super(FDCDepConPayPlanContractInfo.class);
    }
    public boolean add(FDCDepConPayPlanContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanContractInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanContractInfo get(int index)
    {
        return(FDCDepConPayPlanContractInfo)getObject(index);
    }
    public FDCDepConPayPlanContractInfo get(Object key)
    {
        return(FDCDepConPayPlanContractInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanContractInfo item)
    {
        return super.indexOf(item);
    }
}