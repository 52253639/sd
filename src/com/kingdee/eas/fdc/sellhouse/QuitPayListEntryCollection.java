package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuitPayListEntryCollection extends AbstractObjectCollection 
{
    public QuitPayListEntryCollection()
    {
        super(QuitPayListEntryInfo.class);
    }
    public boolean add(QuitPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuitPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuitPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public QuitPayListEntryInfo get(int index)
    {
        return(QuitPayListEntryInfo)getObject(index);
    }
    public QuitPayListEntryInfo get(Object key)
    {
        return(QuitPayListEntryInfo)getObject(key);
    }
    public void set(int index, QuitPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuitPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuitPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}