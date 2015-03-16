package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHERevCustEntryCollection extends AbstractObjectCollection 
{
    public SHERevCustEntryCollection()
    {
        super(SHERevCustEntryInfo.class);
    }
    public boolean add(SHERevCustEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHERevCustEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHERevCustEntryInfo item)
    {
        return removeObject(item);
    }
    public SHERevCustEntryInfo get(int index)
    {
        return(SHERevCustEntryInfo)getObject(index);
    }
    public SHERevCustEntryInfo get(Object key)
    {
        return(SHERevCustEntryInfo)getObject(key);
    }
    public void set(int index, SHERevCustEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHERevCustEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHERevCustEntryInfo item)
    {
        return super.indexOf(item);
    }
}