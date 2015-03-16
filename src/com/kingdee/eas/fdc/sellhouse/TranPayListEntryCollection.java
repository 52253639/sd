package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TranPayListEntryCollection extends AbstractObjectCollection 
{
    public TranPayListEntryCollection()
    {
        super(TranPayListEntryInfo.class);
    }
    public boolean add(TranPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TranPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TranPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public TranPayListEntryInfo get(int index)
    {
        return(TranPayListEntryInfo)getObject(index);
    }
    public TranPayListEntryInfo get(Object key)
    {
        return(TranPayListEntryInfo)getObject(key);
    }
    public void set(int index, TranPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TranPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TranPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}