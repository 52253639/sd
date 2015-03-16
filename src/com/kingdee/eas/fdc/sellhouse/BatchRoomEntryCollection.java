package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BatchRoomEntryCollection extends AbstractObjectCollection 
{
    public BatchRoomEntryCollection()
    {
        super(BatchRoomEntryInfo.class);
    }
    public boolean add(BatchRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BatchRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BatchRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public BatchRoomEntryInfo get(int index)
    {
        return(BatchRoomEntryInfo)getObject(index);
    }
    public BatchRoomEntryInfo get(Object key)
    {
        return(BatchRoomEntryInfo)getObject(key);
    }
    public void set(int index, BatchRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BatchRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BatchRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}