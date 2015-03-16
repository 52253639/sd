package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SignRoomAttachmentEntryCollection extends AbstractObjectCollection 
{
    public SignRoomAttachmentEntryCollection()
    {
        super(SignRoomAttachmentEntryInfo.class);
    }
    public boolean add(SignRoomAttachmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SignRoomAttachmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SignRoomAttachmentEntryInfo item)
    {
        return removeObject(item);
    }
    public SignRoomAttachmentEntryInfo get(int index)
    {
        return(SignRoomAttachmentEntryInfo)getObject(index);
    }
    public SignRoomAttachmentEntryInfo get(Object key)
    {
        return(SignRoomAttachmentEntryInfo)getObject(key);
    }
    public void set(int index, SignRoomAttachmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SignRoomAttachmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SignRoomAttachmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}