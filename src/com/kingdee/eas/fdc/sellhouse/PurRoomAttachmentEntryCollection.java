package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurRoomAttachmentEntryCollection extends AbstractObjectCollection 
{
    public PurRoomAttachmentEntryCollection()
    {
        super(PurRoomAttachmentEntryInfo.class);
    }
    public boolean add(PurRoomAttachmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurRoomAttachmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurRoomAttachmentEntryInfo item)
    {
        return removeObject(item);
    }
    public PurRoomAttachmentEntryInfo get(int index)
    {
        return(PurRoomAttachmentEntryInfo)getObject(index);
    }
    public PurRoomAttachmentEntryInfo get(Object key)
    {
        return(PurRoomAttachmentEntryInfo)getObject(key);
    }
    public void set(int index, PurRoomAttachmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurRoomAttachmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurRoomAttachmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}