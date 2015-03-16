package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostIndexConfigEntryCollection extends AbstractObjectCollection 
{
    public CostIndexConfigEntryCollection()
    {
        super(CostIndexConfigEntryInfo.class);
    }
    public boolean add(CostIndexConfigEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostIndexConfigEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostIndexConfigEntryInfo item)
    {
        return removeObject(item);
    }
    public CostIndexConfigEntryInfo get(int index)
    {
        return(CostIndexConfigEntryInfo)getObject(index);
    }
    public CostIndexConfigEntryInfo get(Object key)
    {
        return(CostIndexConfigEntryInfo)getObject(key);
    }
    public void set(int index, CostIndexConfigEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostIndexConfigEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostIndexConfigEntryInfo item)
    {
        return super.indexOf(item);
    }
}