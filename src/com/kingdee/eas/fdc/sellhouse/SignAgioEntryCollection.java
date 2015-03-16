package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SignAgioEntryCollection extends AbstractObjectCollection 
{
    public SignAgioEntryCollection()
    {
        super(SignAgioEntryInfo.class);
    }
    public boolean add(SignAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SignAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SignAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public SignAgioEntryInfo get(int index)
    {
        return(SignAgioEntryInfo)getObject(index);
    }
    public SignAgioEntryInfo get(Object key)
    {
        return(SignAgioEntryInfo)getObject(key);
    }
    public void set(int index, SignAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SignAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SignAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}