package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayListEntryCollection extends AbstractObjectCollection 
{
    public PayListEntryCollection()
    {
        super(PayListEntryInfo.class);
    }
    public boolean add(PayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayListEntryInfo item)
    {
        return removeObject(item);
    }
    public PayListEntryInfo get(int index)
    {
        return(PayListEntryInfo)getObject(index);
    }
    public PayListEntryInfo get(Object key)
    {
        return(PayListEntryInfo)getObject(key);
    }
    public void set(int index, PayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}