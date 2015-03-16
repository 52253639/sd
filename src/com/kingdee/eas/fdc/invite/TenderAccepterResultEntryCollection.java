package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenderAccepterResultEntryCollection extends AbstractObjectCollection 
{
    public TenderAccepterResultEntryCollection()
    {
        super(TenderAccepterResultEntryInfo.class);
    }
    public boolean add(TenderAccepterResultEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenderAccepterResultEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenderAccepterResultEntryInfo item)
    {
        return removeObject(item);
    }
    public TenderAccepterResultEntryInfo get(int index)
    {
        return(TenderAccepterResultEntryInfo)getObject(index);
    }
    public TenderAccepterResultEntryInfo get(Object key)
    {
        return(TenderAccepterResultEntryInfo)getObject(key);
    }
    public void set(int index, TenderAccepterResultEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenderAccepterResultEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenderAccepterResultEntryInfo item)
    {
        return super.indexOf(item);
    }
}