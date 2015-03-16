package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReceiveDistillCommisionEntryCollection extends AbstractObjectCollection 
{
    public ReceiveDistillCommisionEntryCollection()
    {
        super(ReceiveDistillCommisionEntryInfo.class);
    }
    public boolean add(ReceiveDistillCommisionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReceiveDistillCommisionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReceiveDistillCommisionEntryInfo item)
    {
        return removeObject(item);
    }
    public ReceiveDistillCommisionEntryInfo get(int index)
    {
        return(ReceiveDistillCommisionEntryInfo)getObject(index);
    }
    public ReceiveDistillCommisionEntryInfo get(Object key)
    {
        return(ReceiveDistillCommisionEntryInfo)getObject(key);
    }
    public void set(int index, ReceiveDistillCommisionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReceiveDistillCommisionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReceiveDistillCommisionEntryInfo item)
    {
        return super.indexOf(item);
    }
}