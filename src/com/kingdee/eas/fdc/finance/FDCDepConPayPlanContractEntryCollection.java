package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanContractEntryCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanContractEntryCollection()
    {
        super(FDCDepConPayPlanContractEntryInfo.class);
    }
    public boolean add(FDCDepConPayPlanContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanContractEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanContractEntryInfo get(int index)
    {
        return(FDCDepConPayPlanContractEntryInfo)getObject(index);
    }
    public FDCDepConPayPlanContractEntryInfo get(Object key)
    {
        return(FDCDepConPayPlanContractEntryInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}