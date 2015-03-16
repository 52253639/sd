package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgencyCollection extends AbstractObjectCollection 
{
    public AgencyCollection()
    {
        super(AgencyInfo.class);
    }
    public boolean add(AgencyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgencyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgencyInfo item)
    {
        return removeObject(item);
    }
    public AgencyInfo get(int index)
    {
        return(AgencyInfo)getObject(index);
    }
    public AgencyInfo get(Object key)
    {
        return(AgencyInfo)getObject(key);
    }
    public void set(int index, AgencyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgencyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgencyInfo item)
    {
        return super.indexOf(item);
    }
}