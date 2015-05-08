package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrefixWBSEntryCollection extends AbstractObjectCollection 
{
    public PrefixWBSEntryCollection()
    {
        super(PrefixWBSEntryInfo.class);
    }
    public boolean add(PrefixWBSEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrefixWBSEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrefixWBSEntryInfo item)
    {
        return removeObject(item);
    }
    public PrefixWBSEntryInfo get(int index)
    {
        return(PrefixWBSEntryInfo)getObject(index);
    }
    public PrefixWBSEntryInfo get(Object key)
    {
        return(PrefixWBSEntryInfo)getObject(key);
    }
    public void set(int index, PrefixWBSEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrefixWBSEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrefixWBSEntryInfo item)
    {
        return super.indexOf(item);
    }
}