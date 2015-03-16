package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenderAccepterResultCollection extends AbstractObjectCollection 
{
    public TenderAccepterResultCollection()
    {
        super(TenderAccepterResultInfo.class);
    }
    public boolean add(TenderAccepterResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenderAccepterResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenderAccepterResultInfo item)
    {
        return removeObject(item);
    }
    public TenderAccepterResultInfo get(int index)
    {
        return(TenderAccepterResultInfo)getObject(index);
    }
    public TenderAccepterResultInfo get(Object key)
    {
        return(TenderAccepterResultInfo)getObject(key);
    }
    public void set(int index, TenderAccepterResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenderAccepterResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenderAccepterResultInfo item)
    {
        return super.indexOf(item);
    }
}