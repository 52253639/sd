package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgioSchemeEntryCollection extends AbstractObjectCollection 
{
    public AgioSchemeEntryCollection()
    {
        super(AgioSchemeEntryInfo.class);
    }
    public boolean add(AgioSchemeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgioSchemeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgioSchemeEntryInfo item)
    {
        return removeObject(item);
    }
    public AgioSchemeEntryInfo get(int index)
    {
        return(AgioSchemeEntryInfo)getObject(index);
    }
    public AgioSchemeEntryInfo get(Object key)
    {
        return(AgioSchemeEntryInfo)getObject(key);
    }
    public void set(int index, AgioSchemeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgioSchemeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgioSchemeEntryInfo item)
    {
        return super.indexOf(item);
    }
}