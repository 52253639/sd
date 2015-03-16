package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RestReceivableEntryCollection extends AbstractObjectCollection 
{
    public RestReceivableEntryCollection()
    {
        super(RestReceivableEntryInfo.class);
    }
    public boolean add(RestReceivableEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RestReceivableEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RestReceivableEntryInfo item)
    {
        return removeObject(item);
    }
    public RestReceivableEntryInfo get(int index)
    {
        return(RestReceivableEntryInfo)getObject(index);
    }
    public RestReceivableEntryInfo get(Object key)
    {
        return(RestReceivableEntryInfo)getObject(key);
    }
    public void set(int index, RestReceivableEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RestReceivableEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RestReceivableEntryInfo item)
    {
        return super.indexOf(item);
    }
}