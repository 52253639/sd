package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangePayListEntryCollection extends AbstractObjectCollection 
{
    public ChangePayListEntryCollection()
    {
        super(ChangePayListEntryInfo.class);
    }
    public boolean add(ChangePayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangePayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangePayListEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangePayListEntryInfo get(int index)
    {
        return(ChangePayListEntryInfo)getObject(index);
    }
    public ChangePayListEntryInfo get(Object key)
    {
        return(ChangePayListEntryInfo)getObject(key);
    }
    public void set(int index, ChangePayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangePayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangePayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}