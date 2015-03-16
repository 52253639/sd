package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TransactionCollection extends AbstractObjectCollection 
{
    public TransactionCollection()
    {
        super(TransactionInfo.class);
    }
    public boolean add(TransactionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TransactionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TransactionInfo item)
    {
        return removeObject(item);
    }
    public TransactionInfo get(int index)
    {
        return(TransactionInfo)getObject(index);
    }
    public TransactionInfo get(Object key)
    {
        return(TransactionInfo)getObject(key);
    }
    public void set(int index, TransactionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TransactionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TransactionInfo item)
    {
        return super.indexOf(item);
    }
}