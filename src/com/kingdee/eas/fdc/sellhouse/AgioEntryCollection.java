package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgioEntryCollection extends AbstractObjectCollection 
{
    public AgioEntryCollection()
    {
        super(AgioEntryInfo.class);
    }
    public boolean add(AgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgioEntryInfo item)
    {
        return removeObject(item);
    }
    public AgioEntryInfo get(int index)
    {
        return(AgioEntryInfo)getObject(index);
    }
    public AgioEntryInfo get(Object key)
    {
        return(AgioEntryInfo)getObject(key);
    }
    public void set(int index, AgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}