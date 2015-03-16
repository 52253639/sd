package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BusinessScopeCollection extends AbstractObjectCollection 
{
    public BusinessScopeCollection()
    {
        super(BusinessScopeInfo.class);
    }
    public boolean add(BusinessScopeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BusinessScopeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BusinessScopeInfo item)
    {
        return removeObject(item);
    }
    public BusinessScopeInfo get(int index)
    {
        return(BusinessScopeInfo)getObject(index);
    }
    public BusinessScopeInfo get(Object key)
    {
        return(BusinessScopeInfo)getObject(key);
    }
    public void set(int index, BusinessScopeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BusinessScopeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BusinessScopeInfo item)
    {
        return super.indexOf(item);
    }
}