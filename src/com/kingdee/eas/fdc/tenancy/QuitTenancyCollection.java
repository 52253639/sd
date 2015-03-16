package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuitTenancyCollection extends AbstractObjectCollection 
{
    public QuitTenancyCollection()
    {
        super(QuitTenancyInfo.class);
    }
    public boolean add(QuitTenancyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuitTenancyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuitTenancyInfo item)
    {
        return removeObject(item);
    }
    public QuitTenancyInfo get(int index)
    {
        return(QuitTenancyInfo)getObject(index);
    }
    public QuitTenancyInfo get(Object key)
    {
        return(QuitTenancyInfo)getObject(key);
    }
    public void set(int index, QuitTenancyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuitTenancyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuitTenancyInfo item)
    {
        return super.indexOf(item);
    }
}