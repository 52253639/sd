package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SubstituteAdjustEntryCollection extends AbstractObjectCollection 
{
    public SubstituteAdjustEntryCollection()
    {
        super(SubstituteAdjustEntryInfo.class);
    }
    public boolean add(SubstituteAdjustEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SubstituteAdjustEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SubstituteAdjustEntryInfo item)
    {
        return removeObject(item);
    }
    public SubstituteAdjustEntryInfo get(int index)
    {
        return(SubstituteAdjustEntryInfo)getObject(index);
    }
    public SubstituteAdjustEntryInfo get(Object key)
    {
        return(SubstituteAdjustEntryInfo)getObject(key);
    }
    public void set(int index, SubstituteAdjustEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SubstituteAdjustEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SubstituteAdjustEntryInfo item)
    {
        return super.indexOf(item);
    }
}