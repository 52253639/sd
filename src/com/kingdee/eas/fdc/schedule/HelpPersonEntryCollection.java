package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HelpPersonEntryCollection extends AbstractObjectCollection 
{
    public HelpPersonEntryCollection()
    {
        super(HelpPersonEntryInfo.class);
    }
    public boolean add(HelpPersonEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HelpPersonEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HelpPersonEntryInfo item)
    {
        return removeObject(item);
    }
    public HelpPersonEntryInfo get(int index)
    {
        return(HelpPersonEntryInfo)getObject(index);
    }
    public HelpPersonEntryInfo get(Object key)
    {
        return(HelpPersonEntryInfo)getObject(key);
    }
    public void set(int index, HelpPersonEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HelpPersonEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HelpPersonEntryInfo item)
    {
        return super.indexOf(item);
    }
}