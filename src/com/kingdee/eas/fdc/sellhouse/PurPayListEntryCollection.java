package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurPayListEntryCollection extends AbstractObjectCollection 
{
    public PurPayListEntryCollection()
    {
        super(PurPayListEntryInfo.class);
    }
    public boolean add(PurPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public PurPayListEntryInfo get(int index)
    {
        return(PurPayListEntryInfo)getObject(index);
    }
    public PurPayListEntryInfo get(Object key)
    {
        return(PurPayListEntryInfo)getObject(key);
    }
    public void set(int index, PurPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}