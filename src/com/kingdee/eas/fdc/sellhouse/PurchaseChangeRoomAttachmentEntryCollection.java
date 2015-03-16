package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeRoomAttachmentEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangeRoomAttachmentEntryCollection()
    {
        super(PurchaseChangeRoomAttachmentEntryInfo.class);
    }
    public boolean add(PurchaseChangeRoomAttachmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeRoomAttachmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeRoomAttachmentEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeRoomAttachmentEntryInfo get(int index)
    {
        return(PurchaseChangeRoomAttachmentEntryInfo)getObject(index);
    }
    public PurchaseChangeRoomAttachmentEntryInfo get(Object key)
    {
        return(PurchaseChangeRoomAttachmentEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeRoomAttachmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeRoomAttachmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeRoomAttachmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}