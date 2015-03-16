package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepConPayPlanNoContractEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepConPayPlanNoContractEntryCollection()
    {
        super(FDCProDepConPayPlanNoContractEntryInfo.class);
    }
    public boolean add(FDCProDepConPayPlanNoContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepConPayPlanNoContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepConPayPlanNoContractEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepConPayPlanNoContractEntryInfo get(int index)
    {
        return(FDCProDepConPayPlanNoContractEntryInfo)getObject(index);
    }
    public FDCProDepConPayPlanNoContractEntryInfo get(Object key)
    {
        return(FDCProDepConPayPlanNoContractEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepConPayPlanNoContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepConPayPlanNoContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepConPayPlanNoContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}