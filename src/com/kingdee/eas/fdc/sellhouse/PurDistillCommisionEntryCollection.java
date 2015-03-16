package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurDistillCommisionEntryCollection extends AbstractObjectCollection 
{
    public PurDistillCommisionEntryCollection()
    {
        super(PurDistillCommisionEntryInfo.class);
    }
    public boolean add(PurDistillCommisionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurDistillCommisionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurDistillCommisionEntryInfo item)
    {
        return removeObject(item);
    }
    public PurDistillCommisionEntryInfo get(int index)
    {
        return(PurDistillCommisionEntryInfo)getObject(index);
    }
    public PurDistillCommisionEntryInfo get(Object key)
    {
        return(PurDistillCommisionEntryInfo)getObject(key);
    }
    public void set(int index, PurDistillCommisionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurDistillCommisionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurDistillCommisionEntryInfo item)
    {
        return super.indexOf(item);
    }
}