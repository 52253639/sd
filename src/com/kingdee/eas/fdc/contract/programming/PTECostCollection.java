package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PTECostCollection extends AbstractObjectCollection 
{
    public PTECostCollection()
    {
        super(PTECostInfo.class);
    }
    public boolean add(PTECostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PTECostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PTECostInfo item)
    {
        return removeObject(item);
    }
    public PTECostInfo get(int index)
    {
        return(PTECostInfo)getObject(index);
    }
    public PTECostInfo get(Object key)
    {
        return(PTECostInfo)getObject(key);
    }
    public void set(int index, PTECostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PTECostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PTECostInfo item)
    {
        return super.indexOf(item);
    }
}