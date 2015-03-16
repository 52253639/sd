package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplServiceTypeCollection extends AbstractObjectCollection 
{
    public FDCSplServiceTypeCollection()
    {
        super(FDCSplServiceTypeInfo.class);
    }
    public boolean add(FDCSplServiceTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplServiceTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplServiceTypeInfo item)
    {
        return removeObject(item);
    }
    public FDCSplServiceTypeInfo get(int index)
    {
        return(FDCSplServiceTypeInfo)getObject(index);
    }
    public FDCSplServiceTypeInfo get(Object key)
    {
        return(FDCSplServiceTypeInfo)getObject(key);
    }
    public void set(int index, FDCSplServiceTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplServiceTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplServiceTypeInfo item)
    {
        return super.indexOf(item);
    }
}