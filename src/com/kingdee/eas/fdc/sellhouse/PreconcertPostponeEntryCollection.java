package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreconcertPostponeEntryCollection extends AbstractObjectCollection 
{
    public PreconcertPostponeEntryCollection()
    {
        super(PreconcertPostponeEntryInfo.class);
    }
    public boolean add(PreconcertPostponeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreconcertPostponeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreconcertPostponeEntryInfo item)
    {
        return removeObject(item);
    }
    public PreconcertPostponeEntryInfo get(int index)
    {
        return(PreconcertPostponeEntryInfo)getObject(index);
    }
    public PreconcertPostponeEntryInfo get(Object key)
    {
        return(PreconcertPostponeEntryInfo)getObject(key);
    }
    public void set(int index, PreconcertPostponeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PreconcertPostponeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreconcertPostponeEntryInfo item)
    {
        return super.indexOf(item);
    }
}