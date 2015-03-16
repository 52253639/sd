package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TranRoomAttachmentEntryCollection extends AbstractObjectCollection 
{
    public TranRoomAttachmentEntryCollection()
    {
        super(TranRoomAttachmentEntryInfo.class);
    }
    public boolean add(TranRoomAttachmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TranRoomAttachmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TranRoomAttachmentEntryInfo item)
    {
        return removeObject(item);
    }
    public TranRoomAttachmentEntryInfo get(int index)
    {
        return(TranRoomAttachmentEntryInfo)getObject(index);
    }
    public TranRoomAttachmentEntryInfo get(Object key)
    {
        return(TranRoomAttachmentEntryInfo)getObject(key);
    }
    public void set(int index, TranRoomAttachmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TranRoomAttachmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TranRoomAttachmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}