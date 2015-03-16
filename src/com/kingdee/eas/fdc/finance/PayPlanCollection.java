package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanCollection extends AbstractObjectCollection 
{
    public PayPlanCollection()
    {
        super(PayPlanInfo.class);
    }
    public boolean add(PayPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanInfo item)
    {
        return removeObject(item);
    }
    public PayPlanInfo get(int index)
    {
        return(PayPlanInfo)getObject(index);
    }
    public PayPlanInfo get(Object key)
    {
        return(PayPlanInfo)getObject(key);
    }
    public void set(int index, PayPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanInfo item)
    {
        return super.indexOf(item);
    }
}