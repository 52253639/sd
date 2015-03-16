package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeEntryCollection extends AbstractObjectCollection 
{
    public ChangeEntryCollection()
    {
        super(ChangeEntryInfo.class);
    }
    public boolean add(ChangeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeEntryInfo get(int index)
    {
        return(ChangeEntryInfo)getObject(index);
    }
    public ChangeEntryInfo get(Object key)
    {
        return(ChangeEntryInfo)getObject(key);
    }
    public void set(int index, ChangeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeEntryInfo item)
    {
        return super.indexOf(item);
    }
}