package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCusBaseDataCollection extends AbstractObjectCollection 
{
    public FDCCusBaseDataCollection()
    {
        super(FDCCusBaseDataInfo.class);
    }
    public boolean add(FDCCusBaseDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCusBaseDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCusBaseDataInfo item)
    {
        return removeObject(item);
    }
    public FDCCusBaseDataInfo get(int index)
    {
        return(FDCCusBaseDataInfo)getObject(index);
    }
    public FDCCusBaseDataInfo get(Object key)
    {
        return(FDCCusBaseDataInfo)getObject(key);
    }
    public void set(int index, FDCCusBaseDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCusBaseDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCusBaseDataInfo item)
    {
        return super.indexOf(item);
    }
}