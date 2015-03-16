package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuitNewPayListEntryCollection extends AbstractObjectCollection 
{
    public QuitNewPayListEntryCollection()
    {
        super(QuitNewPayListEntryInfo.class);
    }
    public boolean add(QuitNewPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuitNewPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuitNewPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public QuitNewPayListEntryInfo get(int index)
    {
        return(QuitNewPayListEntryInfo)getObject(index);
    }
    public QuitNewPayListEntryInfo get(Object key)
    {
        return(QuitNewPayListEntryInfo)getObject(key);
    }
    public void set(int index, QuitNewPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuitNewPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuitNewPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}