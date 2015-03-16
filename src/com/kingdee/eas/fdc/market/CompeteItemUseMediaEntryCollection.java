package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompeteItemUseMediaEntryCollection extends AbstractObjectCollection 
{
    public CompeteItemUseMediaEntryCollection()
    {
        super(CompeteItemUseMediaEntryInfo.class);
    }
    public boolean add(CompeteItemUseMediaEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompeteItemUseMediaEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompeteItemUseMediaEntryInfo item)
    {
        return removeObject(item);
    }
    public CompeteItemUseMediaEntryInfo get(int index)
    {
        return(CompeteItemUseMediaEntryInfo)getObject(index);
    }
    public CompeteItemUseMediaEntryInfo get(Object key)
    {
        return(CompeteItemUseMediaEntryInfo)getObject(key);
    }
    public void set(int index, CompeteItemUseMediaEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompeteItemUseMediaEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompeteItemUseMediaEntryInfo item)
    {
        return super.indexOf(item);
    }
}