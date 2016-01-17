package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IntentionCustomerCollection extends AbstractObjectCollection 
{
    public IntentionCustomerCollection()
    {
        super(IntentionCustomerInfo.class);
    }
    public boolean add(IntentionCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IntentionCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IntentionCustomerInfo item)
    {
        return removeObject(item);
    }
    public IntentionCustomerInfo get(int index)
    {
        return(IntentionCustomerInfo)getObject(index);
    }
    public IntentionCustomerInfo get(Object key)
    {
        return(IntentionCustomerInfo)getObject(key);
    }
    public void set(int index, IntentionCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IntentionCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IntentionCustomerInfo item)
    {
        return super.indexOf(item);
    }
}