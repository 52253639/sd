package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MoneyDefineCollection extends AbstractObjectCollection 
{
    public MoneyDefineCollection()
    {
        super(MoneyDefineInfo.class);
    }
    public boolean add(MoneyDefineInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MoneyDefineCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MoneyDefineInfo item)
    {
        return removeObject(item);
    }
    public MoneyDefineInfo get(int index)
    {
        return(MoneyDefineInfo)getObject(index);
    }
    public MoneyDefineInfo get(Object key)
    {
        return(MoneyDefineInfo)getObject(key);
    }
    public void set(int index, MoneyDefineInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MoneyDefineInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MoneyDefineInfo item)
    {
        return super.indexOf(item);
    }
}