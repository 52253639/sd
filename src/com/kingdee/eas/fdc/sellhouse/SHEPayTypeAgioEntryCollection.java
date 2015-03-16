package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEPayTypeAgioEntryCollection extends AbstractObjectCollection 
{
    public SHEPayTypeAgioEntryCollection()
    {
        super(SHEPayTypeAgioEntryInfo.class);
    }
    public boolean add(SHEPayTypeAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEPayTypeAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEPayTypeAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public SHEPayTypeAgioEntryInfo get(int index)
    {
        return(SHEPayTypeAgioEntryInfo)getObject(index);
    }
    public SHEPayTypeAgioEntryInfo get(Object key)
    {
        return(SHEPayTypeAgioEntryInfo)getObject(key);
    }
    public void set(int index, SHEPayTypeAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEPayTypeAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEPayTypeAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}