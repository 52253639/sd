package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EnterpriseSellPlanCollection extends AbstractObjectCollection 
{
    public EnterpriseSellPlanCollection()
    {
        super(EnterpriseSellPlanInfo.class);
    }
    public boolean add(EnterpriseSellPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EnterpriseSellPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EnterpriseSellPlanInfo item)
    {
        return removeObject(item);
    }
    public EnterpriseSellPlanInfo get(int index)
    {
        return(EnterpriseSellPlanInfo)getObject(index);
    }
    public EnterpriseSellPlanInfo get(Object key)
    {
        return(EnterpriseSellPlanInfo)getObject(key);
    }
    public void set(int index, EnterpriseSellPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EnterpriseSellPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EnterpriseSellPlanInfo item)
    {
        return super.indexOf(item);
    }
}