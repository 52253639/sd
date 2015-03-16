package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PartABOMCollection extends AbstractObjectCollection 
{
    public PartABOMCollection()
    {
        super(PartABOMInfo.class);
    }
    public boolean add(PartABOMInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PartABOMCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PartABOMInfo item)
    {
        return removeObject(item);
    }
    public PartABOMInfo get(int index)
    {
        return(PartABOMInfo)getObject(index);
    }
    public PartABOMInfo get(Object key)
    {
        return(PartABOMInfo)getObject(key);
    }
    public void set(int index, PartABOMInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PartABOMInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PartABOMInfo item)
    {
        return super.indexOf(item);
    }
}