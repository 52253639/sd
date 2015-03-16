package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TransferSourceEntryCollection extends AbstractObjectCollection 
{
    public TransferSourceEntryCollection()
    {
        super(TransferSourceEntryInfo.class);
    }
    public boolean add(TransferSourceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TransferSourceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TransferSourceEntryInfo item)
    {
        return removeObject(item);
    }
    public TransferSourceEntryInfo get(int index)
    {
        return(TransferSourceEntryInfo)getObject(index);
    }
    public TransferSourceEntryInfo get(Object key)
    {
        return(TransferSourceEntryInfo)getObject(key);
    }
    public void set(int index, TransferSourceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TransferSourceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TransferSourceEntryInfo item)
    {
        return super.indexOf(item);
    }
}