package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BOMEntryCollection extends AbstractObjectCollection 
{
    public BOMEntryCollection()
    {
        super(BOMEntryInfo.class);
    }
    public boolean add(BOMEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BOMEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BOMEntryInfo item)
    {
        return removeObject(item);
    }
    public BOMEntryInfo get(int index)
    {
        return(BOMEntryInfo)getObject(index);
    }
    public BOMEntryInfo get(Object key)
    {
        return(BOMEntryInfo)getObject(key);
    }
    public void set(int index, BOMEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BOMEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BOMEntryInfo item)
    {
        return super.indexOf(item);
    }
}