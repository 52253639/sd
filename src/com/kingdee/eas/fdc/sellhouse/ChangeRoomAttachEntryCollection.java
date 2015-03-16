package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeRoomAttachEntryCollection extends AbstractObjectCollection 
{
    public ChangeRoomAttachEntryCollection()
    {
        super(ChangeRoomAttachEntryInfo.class);
    }
    public boolean add(ChangeRoomAttachEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeRoomAttachEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeRoomAttachEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeRoomAttachEntryInfo get(int index)
    {
        return(ChangeRoomAttachEntryInfo)getObject(index);
    }
    public ChangeRoomAttachEntryInfo get(Object key)
    {
        return(ChangeRoomAttachEntryInfo)getObject(key);
    }
    public void set(int index, ChangeRoomAttachEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeRoomAttachEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeRoomAttachEntryInfo item)
    {
        return super.indexOf(item);
    }
}