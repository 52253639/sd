package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostIndexCollection extends AbstractObjectCollection 
{
    public CostIndexCollection()
    {
        super(CostIndexInfo.class);
    }
    public boolean add(CostIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostIndexInfo item)
    {
        return removeObject(item);
    }
    public CostIndexInfo get(int index)
    {
        return(CostIndexInfo)getObject(index);
    }
    public CostIndexInfo get(Object key)
    {
        return(CostIndexInfo)getObject(key);
    }
    public void set(int index, CostIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostIndexInfo item)
    {
        return super.indexOf(item);
    }
}