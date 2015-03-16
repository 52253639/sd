package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NatureEnterpriseCollection extends AbstractObjectCollection 
{
    public NatureEnterpriseCollection()
    {
        super(NatureEnterpriseInfo.class);
    }
    public boolean add(NatureEnterpriseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NatureEnterpriseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NatureEnterpriseInfo item)
    {
        return removeObject(item);
    }
    public NatureEnterpriseInfo get(int index)
    {
        return(NatureEnterpriseInfo)getObject(index);
    }
    public NatureEnterpriseInfo get(Object key)
    {
        return(NatureEnterpriseInfo)getObject(key);
    }
    public void set(int index, NatureEnterpriseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NatureEnterpriseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NatureEnterpriseInfo item)
    {
        return super.indexOf(item);
    }
}