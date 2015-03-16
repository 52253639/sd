package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyModificationPayCollection extends AbstractObjectCollection 
{
    public TenancyModificationPayCollection()
    {
        super(TenancyModificationPayInfo.class);
    }
    public boolean add(TenancyModificationPayInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyModificationPayCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyModificationPayInfo item)
    {
        return removeObject(item);
    }
    public TenancyModificationPayInfo get(int index)
    {
        return(TenancyModificationPayInfo)getObject(index);
    }
    public TenancyModificationPayInfo get(Object key)
    {
        return(TenancyModificationPayInfo)getObject(key);
    }
    public void set(int index, TenancyModificationPayInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyModificationPayInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyModificationPayInfo item)
    {
        return super.indexOf(item);
    }
}