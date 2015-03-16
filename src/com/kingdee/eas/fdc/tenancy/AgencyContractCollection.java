package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgencyContractCollection extends AbstractObjectCollection 
{
    public AgencyContractCollection()
    {
        super(AgencyContractInfo.class);
    }
    public boolean add(AgencyContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgencyContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgencyContractInfo item)
    {
        return removeObject(item);
    }
    public AgencyContractInfo get(int index)
    {
        return(AgencyContractInfo)getObject(index);
    }
    public AgencyContractInfo get(Object key)
    {
        return(AgencyContractInfo)getObject(key);
    }
    public void set(int index, AgencyContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgencyContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgencyContractInfo item)
    {
        return super.indexOf(item);
    }
}