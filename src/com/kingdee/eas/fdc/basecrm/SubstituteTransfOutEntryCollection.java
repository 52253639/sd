package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SubstituteTransfOutEntryCollection extends AbstractObjectCollection 
{
    public SubstituteTransfOutEntryCollection()
    {
        super(SubstituteTransfOutEntryInfo.class);
    }
    public boolean add(SubstituteTransfOutEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SubstituteTransfOutEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SubstituteTransfOutEntryInfo item)
    {
        return removeObject(item);
    }
    public SubstituteTransfOutEntryInfo get(int index)
    {
        return(SubstituteTransfOutEntryInfo)getObject(index);
    }
    public SubstituteTransfOutEntryInfo get(Object key)
    {
        return(SubstituteTransfOutEntryInfo)getObject(key);
    }
    public void set(int index, SubstituteTransfOutEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SubstituteTransfOutEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SubstituteTransfOutEntryInfo item)
    {
        return super.indexOf(item);
    }
}