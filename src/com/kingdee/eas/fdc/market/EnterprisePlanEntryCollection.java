package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EnterprisePlanEntryCollection extends AbstractObjectCollection 
{
    public EnterprisePlanEntryCollection()
    {
        super(EnterprisePlanEntryInfo.class);
    }
    public boolean add(EnterprisePlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EnterprisePlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EnterprisePlanEntryInfo item)
    {
        return removeObject(item);
    }
    public EnterprisePlanEntryInfo get(int index)
    {
        return(EnterprisePlanEntryInfo)getObject(index);
    }
    public EnterprisePlanEntryInfo get(Object key)
    {
        return(EnterprisePlanEntryInfo)getObject(key);
    }
    public void set(int index, EnterprisePlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EnterprisePlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EnterprisePlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}