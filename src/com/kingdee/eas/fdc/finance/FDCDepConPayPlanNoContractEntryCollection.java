package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanNoContractEntryCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanNoContractEntryCollection()
    {
        super(FDCDepConPayPlanNoContractEntryInfo.class);
    }
    public boolean add(FDCDepConPayPlanNoContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanNoContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanNoContractEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanNoContractEntryInfo get(int index)
    {
        return(FDCDepConPayPlanNoContractEntryInfo)getObject(index);
    }
    public FDCDepConPayPlanNoContractEntryInfo get(Object key)
    {
        return(FDCDepConPayPlanNoContractEntryInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanNoContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanNoContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanNoContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}