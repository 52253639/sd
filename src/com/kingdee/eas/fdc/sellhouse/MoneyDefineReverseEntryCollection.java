package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MoneyDefineReverseEntryCollection extends AbstractObjectCollection 
{
    public MoneyDefineReverseEntryCollection()
    {
        super(MoneyDefineReverseEntryInfo.class);
    }
    public boolean add(MoneyDefineReverseEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MoneyDefineReverseEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MoneyDefineReverseEntryInfo item)
    {
        return removeObject(item);
    }
    public MoneyDefineReverseEntryInfo get(int index)
    {
        return(MoneyDefineReverseEntryInfo)getObject(index);
    }
    public MoneyDefineReverseEntryInfo get(Object key)
    {
        return(MoneyDefineReverseEntryInfo)getObject(key);
    }
    public void set(int index, MoneyDefineReverseEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MoneyDefineReverseEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MoneyDefineReverseEntryInfo item)
    {
        return super.indexOf(item);
    }
}