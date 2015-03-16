package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomAttachmentEntryCollection extends AbstractObjectCollection 
{
    public RoomAttachmentEntryCollection()
    {
        super(RoomAttachmentEntryInfo.class);
    }
    public boolean add(RoomAttachmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomAttachmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomAttachmentEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomAttachmentEntryInfo get(int index)
    {
        return(RoomAttachmentEntryInfo)getObject(index);
    }
    public RoomAttachmentEntryInfo get(Object key)
    {
        return(RoomAttachmentEntryInfo)getObject(key);
    }
    public void set(int index, RoomAttachmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomAttachmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomAttachmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}