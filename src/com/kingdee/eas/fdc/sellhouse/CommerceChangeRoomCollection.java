package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommerceChangeRoomCollection extends AbstractObjectCollection 
{
    public CommerceChangeRoomCollection()
    {
        super(CommerceChangeRoomInfo.class);
    }
    public boolean add(CommerceChangeRoomInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommerceChangeRoomCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommerceChangeRoomInfo item)
    {
        return removeObject(item);
    }
    public CommerceChangeRoomInfo get(int index)
    {
        return(CommerceChangeRoomInfo)getObject(index);
    }
    public CommerceChangeRoomInfo get(Object key)
    {
        return(CommerceChangeRoomInfo)getObject(key);
    }
    public void set(int index, CommerceChangeRoomInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommerceChangeRoomInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommerceChangeRoomInfo item)
    {
        return super.indexOf(item);
    }
}