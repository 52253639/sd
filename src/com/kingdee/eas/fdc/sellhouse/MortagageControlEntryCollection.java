package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MortagageControlEntryCollection extends AbstractObjectCollection 
{
    public MortagageControlEntryCollection()
    {
        super(MortagageControlEntryInfo.class);
    }
    public boolean add(MortagageControlEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MortagageControlEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MortagageControlEntryInfo item)
    {
        return removeObject(item);
    }
    public MortagageControlEntryInfo get(int index)
    {
        return(MortagageControlEntryInfo)getObject(index);
    }
    public MortagageControlEntryInfo get(Object key)
    {
        return(MortagageControlEntryInfo)getObject(key);
    }
    public void set(int index, MortagageControlEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MortagageControlEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MortagageControlEntryInfo item)
    {
        return super.indexOf(item);
    }
}