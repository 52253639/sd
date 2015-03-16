package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCusBaseDataGroupCollection extends AbstractObjectCollection 
{
    public FDCCusBaseDataGroupCollection()
    {
        super(FDCCusBaseDataGroupInfo.class);
    }
    public boolean add(FDCCusBaseDataGroupInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCusBaseDataGroupCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCusBaseDataGroupInfo item)
    {
        return removeObject(item);
    }
    public FDCCusBaseDataGroupInfo get(int index)
    {
        return(FDCCusBaseDataGroupInfo)getObject(index);
    }
    public FDCCusBaseDataGroupInfo get(Object key)
    {
        return(FDCCusBaseDataGroupInfo)getObject(key);
    }
    public void set(int index, FDCCusBaseDataGroupInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCusBaseDataGroupInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCusBaseDataGroupInfo item)
    {
        return super.indexOf(item);
    }
}