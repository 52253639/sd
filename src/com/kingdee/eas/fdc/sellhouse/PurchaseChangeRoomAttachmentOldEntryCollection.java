package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeRoomAttachmentOldEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangeRoomAttachmentOldEntryCollection()
    {
        super(PurchaseChangeRoomAttachmentOldEntryInfo.class);
    }
    public boolean add(PurchaseChangeRoomAttachmentOldEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeRoomAttachmentOldEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeRoomAttachmentOldEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeRoomAttachmentOldEntryInfo get(int index)
    {
        return(PurchaseChangeRoomAttachmentOldEntryInfo)getObject(index);
    }
    public PurchaseChangeRoomAttachmentOldEntryInfo get(Object key)
    {
        return(PurchaseChangeRoomAttachmentOldEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeRoomAttachmentOldEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeRoomAttachmentOldEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeRoomAttachmentOldEntryInfo item)
    {
        return super.indexOf(item);
    }
}