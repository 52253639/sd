package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AcctConCostCollection extends AbstractObjectCollection 
{
    public AcctConCostCollection()
    {
        super(AcctConCostInfo.class);
    }
    public boolean add(AcctConCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AcctConCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AcctConCostInfo item)
    {
        return removeObject(item);
    }
    public AcctConCostInfo get(int index)
    {
        return(AcctConCostInfo)getObject(index);
    }
    public AcctConCostInfo get(Object key)
    {
        return(AcctConCostInfo)getObject(key);
    }
    public void set(int index, AcctConCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AcctConCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AcctConCostInfo item)
    {
        return super.indexOf(item);
    }
}