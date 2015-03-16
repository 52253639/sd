package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BoutiqueRoomTypeCollection extends AbstractObjectCollection 
{
    public BoutiqueRoomTypeCollection()
    {
        super(BoutiqueRoomTypeInfo.class);
    }
    public boolean add(BoutiqueRoomTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BoutiqueRoomTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BoutiqueRoomTypeInfo item)
    {
        return removeObject(item);
    }
    public BoutiqueRoomTypeInfo get(int index)
    {
        return(BoutiqueRoomTypeInfo)getObject(index);
    }
    public BoutiqueRoomTypeInfo get(Object key)
    {
        return(BoutiqueRoomTypeInfo)getObject(key);
    }
    public void set(int index, BoutiqueRoomTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BoutiqueRoomTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BoutiqueRoomTypeInfo item)
    {
        return super.indexOf(item);
    }
}