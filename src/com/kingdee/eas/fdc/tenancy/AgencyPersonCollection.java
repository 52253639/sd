package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgencyPersonCollection extends AbstractObjectCollection 
{
    public AgencyPersonCollection()
    {
        super(AgencyPersonInfo.class);
    }
    public boolean add(AgencyPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgencyPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgencyPersonInfo item)
    {
        return removeObject(item);
    }
    public AgencyPersonInfo get(int index)
    {
        return(AgencyPersonInfo)getObject(index);
    }
    public AgencyPersonInfo get(Object key)
    {
        return(AgencyPersonInfo)getObject(key);
    }
    public void set(int index, AgencyPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgencyPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgencyPersonInfo item)
    {
        return super.indexOf(item);
    }
}