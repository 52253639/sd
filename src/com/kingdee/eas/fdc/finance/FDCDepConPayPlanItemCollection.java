package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCDepConPayPlanItemCollection extends AbstractObjectCollection 
{
    public FDCDepConPayPlanItemCollection()
    {
        super(FDCDepConPayPlanItemInfo.class);
    }
    public boolean add(FDCDepConPayPlanItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCDepConPayPlanItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCDepConPayPlanItemInfo item)
    {
        return removeObject(item);
    }
    public FDCDepConPayPlanItemInfo get(int index)
    {
        return(FDCDepConPayPlanItemInfo)getObject(index);
    }
    public FDCDepConPayPlanItemInfo get(Object key)
    {
        return(FDCDepConPayPlanItemInfo)getObject(key);
    }
    public void set(int index, FDCDepConPayPlanItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCDepConPayPlanItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCDepConPayPlanItemInfo item)
    {
        return super.indexOf(item);
    }
}