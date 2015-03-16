package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DirectAccepterResultCollection extends AbstractObjectCollection 
{
    public DirectAccepterResultCollection()
    {
        super(DirectAccepterResultInfo.class);
    }
    public boolean add(DirectAccepterResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DirectAccepterResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DirectAccepterResultInfo item)
    {
        return removeObject(item);
    }
    public DirectAccepterResultInfo get(int index)
    {
        return(DirectAccepterResultInfo)getObject(index);
    }
    public DirectAccepterResultInfo get(Object key)
    {
        return(DirectAccepterResultInfo)getObject(key);
    }
    public void set(int index, DirectAccepterResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DirectAccepterResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DirectAccepterResultInfo item)
    {
        return super.indexOf(item);
    }
}