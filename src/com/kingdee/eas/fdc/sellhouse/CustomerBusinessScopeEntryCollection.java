package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerBusinessScopeEntryCollection extends AbstractObjectCollection 
{
    public CustomerBusinessScopeEntryCollection()
    {
        super(CustomerBusinessScopeEntryInfo.class);
    }
    public boolean add(CustomerBusinessScopeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerBusinessScopeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerBusinessScopeEntryInfo item)
    {
        return removeObject(item);
    }
    public CustomerBusinessScopeEntryInfo get(int index)
    {
        return(CustomerBusinessScopeEntryInfo)getObject(index);
    }
    public CustomerBusinessScopeEntryInfo get(Object key)
    {
        return(CustomerBusinessScopeEntryInfo)getObject(key);
    }
    public void set(int index, CustomerBusinessScopeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerBusinessScopeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerBusinessScopeEntryInfo item)
    {
        return super.indexOf(item);
    }
}