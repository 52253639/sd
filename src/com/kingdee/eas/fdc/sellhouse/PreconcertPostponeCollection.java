package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PreconcertPostponeCollection extends AbstractObjectCollection 
{
    public PreconcertPostponeCollection()
    {
        super(PreconcertPostponeInfo.class);
    }
    public boolean add(PreconcertPostponeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PreconcertPostponeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PreconcertPostponeInfo item)
    {
        return removeObject(item);
    }
    public PreconcertPostponeInfo get(int index)
    {
        return(PreconcertPostponeInfo)getObject(index);
    }
    public PreconcertPostponeInfo get(Object key)
    {
        return(PreconcertPostponeInfo)getObject(key);
    }
    public void set(int index, PreconcertPostponeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PreconcertPostponeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PreconcertPostponeInfo item)
    {
        return super.indexOf(item);
    }
}