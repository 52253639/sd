package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostIndexConfigCollection extends AbstractObjectCollection 
{
    public CostIndexConfigCollection()
    {
        super(CostIndexConfigInfo.class);
    }
    public boolean add(CostIndexConfigInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostIndexConfigCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostIndexConfigInfo item)
    {
        return removeObject(item);
    }
    public CostIndexConfigInfo get(int index)
    {
        return(CostIndexConfigInfo)getObject(index);
    }
    public CostIndexConfigInfo get(Object key)
    {
        return(CostIndexConfigInfo)getObject(key);
    }
    public void set(int index, CostIndexConfigInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostIndexConfigInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostIndexConfigInfo item)
    {
        return super.indexOf(item);
    }
}