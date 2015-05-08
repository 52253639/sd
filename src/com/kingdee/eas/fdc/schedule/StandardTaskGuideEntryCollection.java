package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StandardTaskGuideEntryCollection extends AbstractObjectCollection 
{
    public StandardTaskGuideEntryCollection()
    {
        super(StandardTaskGuideEntryInfo.class);
    }
    public boolean add(StandardTaskGuideEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StandardTaskGuideEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StandardTaskGuideEntryInfo item)
    {
        return removeObject(item);
    }
    public StandardTaskGuideEntryInfo get(int index)
    {
        return(StandardTaskGuideEntryInfo)getObject(index);
    }
    public StandardTaskGuideEntryInfo get(Object key)
    {
        return(StandardTaskGuideEntryInfo)getObject(key);
    }
    public void set(int index, StandardTaskGuideEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StandardTaskGuideEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StandardTaskGuideEntryInfo item)
    {
        return super.indexOf(item);
    }
}