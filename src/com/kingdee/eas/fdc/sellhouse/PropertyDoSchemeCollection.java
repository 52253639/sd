package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PropertyDoSchemeCollection extends AbstractObjectCollection 
{
    public PropertyDoSchemeCollection()
    {
        super(PropertyDoSchemeInfo.class);
    }
    public boolean add(PropertyDoSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PropertyDoSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PropertyDoSchemeInfo item)
    {
        return removeObject(item);
    }
    public PropertyDoSchemeInfo get(int index)
    {
        return(PropertyDoSchemeInfo)getObject(index);
    }
    public PropertyDoSchemeInfo get(Object key)
    {
        return(PropertyDoSchemeInfo)getObject(key);
    }
    public void set(int index, PropertyDoSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PropertyDoSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PropertyDoSchemeInfo item)
    {
        return super.indexOf(item);
    }
}