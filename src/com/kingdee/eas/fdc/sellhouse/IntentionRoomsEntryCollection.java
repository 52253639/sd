package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IntentionRoomsEntryCollection extends AbstractObjectCollection 
{
    public IntentionRoomsEntryCollection()
    {
        super(IntentionRoomsEntryInfo.class);
    }
    public boolean add(IntentionRoomsEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IntentionRoomsEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IntentionRoomsEntryInfo item)
    {
        return removeObject(item);
    }
    public IntentionRoomsEntryInfo get(int index)
    {
        return(IntentionRoomsEntryInfo)getObject(index);
    }
    public IntentionRoomsEntryInfo get(Object key)
    {
        return(IntentionRoomsEntryInfo)getObject(key);
    }
    public void set(int index, IntentionRoomsEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IntentionRoomsEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IntentionRoomsEntryInfo item)
    {
        return super.indexOf(item);
    }
}