package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CooperateModeCollection extends AbstractObjectCollection 
{
    public CooperateModeCollection()
    {
        super(CooperateModeInfo.class);
    }
    public boolean add(CooperateModeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CooperateModeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CooperateModeInfo item)
    {
        return removeObject(item);
    }
    public CooperateModeInfo get(int index)
    {
        return(CooperateModeInfo)getObject(index);
    }
    public CooperateModeInfo get(Object key)
    {
        return(CooperateModeInfo)getObject(key);
    }
    public void set(int index, CooperateModeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CooperateModeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CooperateModeInfo item)
    {
        return super.indexOf(item);
    }
}