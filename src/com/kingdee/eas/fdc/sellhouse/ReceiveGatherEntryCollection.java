package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReceiveGatherEntryCollection extends AbstractObjectCollection 
{
    public ReceiveGatherEntryCollection()
    {
        super(ReceiveGatherEntryInfo.class);
    }
    public boolean add(ReceiveGatherEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReceiveGatherEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReceiveGatherEntryInfo item)
    {
        return removeObject(item);
    }
    public ReceiveGatherEntryInfo get(int index)
    {
        return(ReceiveGatherEntryInfo)getObject(index);
    }
    public ReceiveGatherEntryInfo get(Object key)
    {
        return(ReceiveGatherEntryInfo)getObject(key);
    }
    public void set(int index, ReceiveGatherEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReceiveGatherEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReceiveGatherEntryInfo item)
    {
        return super.indexOf(item);
    }
}