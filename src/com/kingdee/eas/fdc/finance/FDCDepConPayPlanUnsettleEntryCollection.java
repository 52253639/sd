package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanUnsettleEntryCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanUnsettleEntryCollection()
    {
        super(FDCDepConPayPlanUnsettleEntryInfo.class);
    }
    public boolean add(FDCDepConPayPlanUnsettleEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanUnsettleEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanUnsettleEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanUnsettleEntryInfo get(int index)
    {
        return(FDCDepConPayPlanUnsettleEntryInfo)getObject(index);
    }
    public FDCDepConPayPlanUnsettleEntryInfo get(Object key)
    {
        return(FDCDepConPayPlanUnsettleEntryInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanUnsettleEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanUnsettleEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanUnsettleEntryInfo item)
    {
        return super.indexOf(item);
    }
}