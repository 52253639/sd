package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepConPayPlanUnsetEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepConPayPlanUnsetEntryCollection()
    {
        super(FDCProDepConPayPlanUnsetEntryInfo.class);
    }
    public boolean add(FDCProDepConPayPlanUnsetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepConPayPlanUnsetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepConPayPlanUnsetEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepConPayPlanUnsetEntryInfo get(int index)
    {
        return(FDCProDepConPayPlanUnsetEntryInfo)getObject(index);
    }
    public FDCProDepConPayPlanUnsetEntryInfo get(Object key)
    {
        return(FDCProDepConPayPlanUnsetEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepConPayPlanUnsetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepConPayPlanUnsetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepConPayPlanUnsetEntryInfo item)
    {
        return super.indexOf(item);
    }
}