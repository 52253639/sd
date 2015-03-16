package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseRoomAttachmentEntryCollection extends AbstractObjectCollection 
{
    public PurchaseRoomAttachmentEntryCollection()
    {
        super(PurchaseRoomAttachmentEntryInfo.class);
    }
    public boolean add(PurchaseRoomAttachmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseRoomAttachmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseRoomAttachmentEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseRoomAttachmentEntryInfo get(int index)
    {
        return(PurchaseRoomAttachmentEntryInfo)getObject(index);
    }
    public PurchaseRoomAttachmentEntryInfo get(Object key)
    {
        return(PurchaseRoomAttachmentEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseRoomAttachmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseRoomAttachmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseRoomAttachmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}