package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RestReceivableCollection extends AbstractObjectCollection 
{
    public RestReceivableCollection()
    {
        super(RestReceivableInfo.class);
    }
    public boolean add(RestReceivableInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RestReceivableCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RestReceivableInfo item)
    {
        return removeObject(item);
    }
    public RestReceivableInfo get(int index)
    {
        return(RestReceivableInfo)getObject(index);
    }
    public RestReceivableInfo get(Object key)
    {
        return(RestReceivableInfo)getObject(key);
    }
    public void set(int index, RestReceivableInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RestReceivableInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RestReceivableInfo item)
    {
        return super.indexOf(item);
    }
}