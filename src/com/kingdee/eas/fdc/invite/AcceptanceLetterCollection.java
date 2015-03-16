package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AcceptanceLetterCollection extends AbstractObjectCollection 
{
    public AcceptanceLetterCollection()
    {
        super(AcceptanceLetterInfo.class);
    }
    public boolean add(AcceptanceLetterInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AcceptanceLetterCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AcceptanceLetterInfo item)
    {
        return removeObject(item);
    }
    public AcceptanceLetterInfo get(int index)
    {
        return(AcceptanceLetterInfo)getObject(index);
    }
    public AcceptanceLetterInfo get(Object key)
    {
        return(AcceptanceLetterInfo)getObject(key);
    }
    public void set(int index, AcceptanceLetterInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AcceptanceLetterInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AcceptanceLetterInfo item)
    {
        return super.indexOf(item);
    }
}