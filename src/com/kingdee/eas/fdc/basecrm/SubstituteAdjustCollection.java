package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SubstituteAdjustCollection extends AbstractObjectCollection 
{
    public SubstituteAdjustCollection()
    {
        super(SubstituteAdjustInfo.class);
    }
    public boolean add(SubstituteAdjustInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SubstituteAdjustCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SubstituteAdjustInfo item)
    {
        return removeObject(item);
    }
    public SubstituteAdjustInfo get(int index)
    {
        return(SubstituteAdjustInfo)getObject(index);
    }
    public SubstituteAdjustInfo get(Object key)
    {
        return(SubstituteAdjustInfo)getObject(key);
    }
    public void set(int index, SubstituteAdjustInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SubstituteAdjustInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SubstituteAdjustInfo item)
    {
        return super.indexOf(item);
    }
}