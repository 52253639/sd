package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChooseRoomCusEntryCollection extends AbstractObjectCollection 
{
    public ChooseRoomCusEntryCollection()
    {
        super(ChooseRoomCusEntryInfo.class);
    }
    public boolean add(ChooseRoomCusEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChooseRoomCusEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChooseRoomCusEntryInfo item)
    {
        return removeObject(item);
    }
    public ChooseRoomCusEntryInfo get(int index)
    {
        return(ChooseRoomCusEntryInfo)getObject(index);
    }
    public ChooseRoomCusEntryInfo get(Object key)
    {
        return(ChooseRoomCusEntryInfo)getObject(key);
    }
    public void set(int index, ChooseRoomCusEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChooseRoomCusEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChooseRoomCusEntryInfo item)
    {
        return super.indexOf(item);
    }
}