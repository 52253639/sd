package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BoutiqueRoomTypeTreeCollection extends AbstractObjectCollection 
{
    public BoutiqueRoomTypeTreeCollection()
    {
        super(BoutiqueRoomTypeTreeInfo.class);
    }
    public boolean add(BoutiqueRoomTypeTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BoutiqueRoomTypeTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BoutiqueRoomTypeTreeInfo item)
    {
        return removeObject(item);
    }
    public BoutiqueRoomTypeTreeInfo get(int index)
    {
        return(BoutiqueRoomTypeTreeInfo)getObject(index);
    }
    public BoutiqueRoomTypeTreeInfo get(Object key)
    {
        return(BoutiqueRoomTypeTreeInfo)getObject(key);
    }
    public void set(int index, BoutiqueRoomTypeTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BoutiqueRoomTypeTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BoutiqueRoomTypeTreeInfo item)
    {
        return super.indexOf(item);
    }
}