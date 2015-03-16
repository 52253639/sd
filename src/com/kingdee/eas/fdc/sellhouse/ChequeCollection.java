package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChequeCollection extends AbstractObjectCollection 
{
    public ChequeCollection()
    {
        super(ChequeInfo.class);
    }
    public boolean add(ChequeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChequeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChequeInfo item)
    {
        return removeObject(item);
    }
    public ChequeInfo get(int index)
    {
        return(ChequeInfo)getObject(index);
    }
    public ChequeInfo get(Object key)
    {
        return(ChequeInfo)getObject(key);
    }
    public void set(int index, ChequeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChequeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChequeInfo item)
    {
        return super.indexOf(item);
    }
}