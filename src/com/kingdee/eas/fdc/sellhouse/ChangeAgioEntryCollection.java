package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeAgioEntryCollection extends AbstractObjectCollection 
{
    public ChangeAgioEntryCollection()
    {
        super(ChangeAgioEntryInfo.class);
    }
    public boolean add(ChangeAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeAgioEntryInfo get(int index)
    {
        return(ChangeAgioEntryInfo)getObject(index);
    }
    public ChangeAgioEntryInfo get(Object key)
    {
        return(ChangeAgioEntryInfo)getObject(key);
    }
    public void set(int index, ChangeAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}