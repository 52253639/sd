package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseTransactionCollection extends AbstractObjectCollection 
{
    public BaseTransactionCollection()
    {
        super(BaseTransactionInfo.class);
    }
    public boolean add(BaseTransactionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseTransactionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseTransactionInfo item)
    {
        return removeObject(item);
    }
    public BaseTransactionInfo get(int index)
    {
        return(BaseTransactionInfo)getObject(index);
    }
    public BaseTransactionInfo get(Object key)
    {
        return(BaseTransactionInfo)getObject(key);
    }
    public void set(int index, BaseTransactionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseTransactionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseTransactionInfo item)
    {
        return super.indexOf(item);
    }
}