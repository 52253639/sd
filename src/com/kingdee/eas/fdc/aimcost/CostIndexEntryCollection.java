package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostIndexEntryCollection extends AbstractObjectCollection 
{
    public CostIndexEntryCollection()
    {
        super(CostIndexEntryInfo.class);
    }
    public boolean add(CostIndexEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostIndexEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostIndexEntryInfo item)
    {
        return removeObject(item);
    }
    public CostIndexEntryInfo get(int index)
    {
        return(CostIndexEntryInfo)getObject(index);
    }
    public CostIndexEntryInfo get(Object key)
    {
        return(CostIndexEntryInfo)getObject(key);
    }
    public void set(int index, CostIndexEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostIndexEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostIndexEntryInfo item)
    {
        return super.indexOf(item);
    }
}