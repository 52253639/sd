package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MoneyDefineSellProjectCollection extends AbstractObjectCollection 
{
    public MoneyDefineSellProjectCollection()
    {
        super(MoneyDefineSellProjectInfo.class);
    }
    public boolean add(MoneyDefineSellProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MoneyDefineSellProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MoneyDefineSellProjectInfo item)
    {
        return removeObject(item);
    }
    public MoneyDefineSellProjectInfo get(int index)
    {
        return(MoneyDefineSellProjectInfo)getObject(index);
    }
    public MoneyDefineSellProjectInfo get(Object key)
    {
        return(MoneyDefineSellProjectInfo)getObject(key);
    }
    public void set(int index, MoneyDefineSellProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MoneyDefineSellProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MoneyDefineSellProjectInfo item)
    {
        return super.indexOf(item);
    }
}