package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PartABOMEntryCollection extends AbstractObjectCollection 
{
    public PartABOMEntryCollection()
    {
        super(PartABOMEntryInfo.class);
    }
    public boolean add(PartABOMEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PartABOMEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PartABOMEntryInfo item)
    {
        return removeObject(item);
    }
    public PartABOMEntryInfo get(int index)
    {
        return(PartABOMEntryInfo)getObject(index);
    }
    public PartABOMEntryInfo get(Object key)
    {
        return(PartABOMEntryInfo)getObject(key);
    }
    public void set(int index, PartABOMEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PartABOMEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PartABOMEntryInfo item)
    {
        return super.indexOf(item);
    }
}