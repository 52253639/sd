package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EnterprisePlanCollection extends AbstractObjectCollection 
{
    public EnterprisePlanCollection()
    {
        super(EnterprisePlanInfo.class);
    }
    public boolean add(EnterprisePlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EnterprisePlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EnterprisePlanInfo item)
    {
        return removeObject(item);
    }
    public EnterprisePlanInfo get(int index)
    {
        return(EnterprisePlanInfo)getObject(index);
    }
    public EnterprisePlanInfo get(Object key)
    {
        return(EnterprisePlanInfo)getObject(key);
    }
    public void set(int index, EnterprisePlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EnterprisePlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EnterprisePlanInfo item)
    {
        return super.indexOf(item);
    }
}