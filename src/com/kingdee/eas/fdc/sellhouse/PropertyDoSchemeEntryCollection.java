package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PropertyDoSchemeEntryCollection extends AbstractObjectCollection 
{
    public PropertyDoSchemeEntryCollection()
    {
        super(PropertyDoSchemeEntryInfo.class);
    }
    public boolean add(PropertyDoSchemeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PropertyDoSchemeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PropertyDoSchemeEntryInfo item)
    {
        return removeObject(item);
    }
    public PropertyDoSchemeEntryInfo get(int index)
    {
        return(PropertyDoSchemeEntryInfo)getObject(index);
    }
    public PropertyDoSchemeEntryInfo get(Object key)
    {
        return(PropertyDoSchemeEntryInfo)getObject(key);
    }
    public void set(int index, PropertyDoSchemeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PropertyDoSchemeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PropertyDoSchemeEntryInfo item)
    {
        return super.indexOf(item);
    }
}