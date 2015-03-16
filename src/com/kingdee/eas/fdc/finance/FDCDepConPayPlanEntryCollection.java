package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanEntryCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanEntryCollection()
    {
        super(FDCDepConPayPlanEntryInfo.class);
    }
    public boolean add(FDCDepConPayPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanEntryInfo get(int index)
    {
        return(FDCDepConPayPlanEntryInfo)getObject(index);
    }
    public FDCDepConPayPlanEntryInfo get(Object key)
    {
        return(FDCDepConPayPlanEntryInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}