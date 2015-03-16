package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DirectAccepterResultEntryCollection extends AbstractObjectCollection 
{
    public DirectAccepterResultEntryCollection()
    {
        super(DirectAccepterResultEntryInfo.class);
    }
    public boolean add(DirectAccepterResultEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DirectAccepterResultEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DirectAccepterResultEntryInfo item)
    {
        return removeObject(item);
    }
    public DirectAccepterResultEntryInfo get(int index)
    {
        return(DirectAccepterResultEntryInfo)getObject(index);
    }
    public DirectAccepterResultEntryInfo get(Object key)
    {
        return(DirectAccepterResultEntryInfo)getObject(key);
    }
    public void set(int index, DirectAccepterResultEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DirectAccepterResultEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DirectAccepterResultEntryInfo item)
    {
        return super.indexOf(item);
    }
}