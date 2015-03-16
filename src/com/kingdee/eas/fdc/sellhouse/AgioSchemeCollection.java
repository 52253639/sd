package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgioSchemeCollection extends AbstractObjectCollection 
{
    public AgioSchemeCollection()
    {
        super(AgioSchemeInfo.class);
    }
    public boolean add(AgioSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgioSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgioSchemeInfo item)
    {
        return removeObject(item);
    }
    public AgioSchemeInfo get(int index)
    {
        return(AgioSchemeInfo)getObject(index);
    }
    public AgioSchemeInfo get(Object key)
    {
        return(AgioSchemeInfo)getObject(key);
    }
    public void set(int index, AgioSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgioSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgioSchemeInfo item)
    {
        return super.indexOf(item);
    }
}