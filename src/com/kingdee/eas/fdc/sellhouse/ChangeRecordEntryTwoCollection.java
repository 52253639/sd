package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeRecordEntryTwoCollection extends AbstractObjectCollection 
{
    public ChangeRecordEntryTwoCollection()
    {
        super(ChangeRecordEntryTwoInfo.class);
    }
    public boolean add(ChangeRecordEntryTwoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeRecordEntryTwoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeRecordEntryTwoInfo item)
    {
        return removeObject(item);
    }
    public ChangeRecordEntryTwoInfo get(int index)
    {
        return(ChangeRecordEntryTwoInfo)getObject(index);
    }
    public ChangeRecordEntryTwoInfo get(Object key)
    {
        return(ChangeRecordEntryTwoInfo)getObject(key);
    }
    public void set(int index, ChangeRecordEntryTwoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeRecordEntryTwoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeRecordEntryTwoInfo item)
    {
        return super.indexOf(item);
    }
}