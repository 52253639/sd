package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrePurchaseRoomAttachmentEntryCollection extends AbstractObjectCollection 
{
    public PrePurchaseRoomAttachmentEntryCollection()
    {
        super(PrePurchaseRoomAttachmentEntryInfo.class);
    }
    public boolean add(PrePurchaseRoomAttachmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrePurchaseRoomAttachmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrePurchaseRoomAttachmentEntryInfo item)
    {
        return removeObject(item);
    }
    public PrePurchaseRoomAttachmentEntryInfo get(int index)
    {
        return(PrePurchaseRoomAttachmentEntryInfo)getObject(index);
    }
    public PrePurchaseRoomAttachmentEntryInfo get(Object key)
    {
        return(PrePurchaseRoomAttachmentEntryInfo)getObject(key);
    }
    public void set(int index, PrePurchaseRoomAttachmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrePurchaseRoomAttachmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrePurchaseRoomAttachmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}