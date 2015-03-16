package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PropertyDoSchemeEntryTwoCollection extends AbstractObjectCollection 
{
    public PropertyDoSchemeEntryTwoCollection()
    {
        super(PropertyDoSchemeEntryTwoInfo.class);
    }
    public boolean add(PropertyDoSchemeEntryTwoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PropertyDoSchemeEntryTwoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PropertyDoSchemeEntryTwoInfo item)
    {
        return removeObject(item);
    }
    public PropertyDoSchemeEntryTwoInfo get(int index)
    {
        return(PropertyDoSchemeEntryTwoInfo)getObject(index);
    }
    public PropertyDoSchemeEntryTwoInfo get(Object key)
    {
        return(PropertyDoSchemeEntryTwoInfo)getObject(key);
    }
    public void set(int index, PropertyDoSchemeEntryTwoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PropertyDoSchemeEntryTwoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PropertyDoSchemeEntryTwoInfo item)
    {
        return super.indexOf(item);
    }
}