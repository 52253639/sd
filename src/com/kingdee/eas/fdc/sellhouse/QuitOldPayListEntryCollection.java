package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuitOldPayListEntryCollection extends AbstractObjectCollection 
{
    public QuitOldPayListEntryCollection()
    {
        super(QuitOldPayListEntryInfo.class);
    }
    public boolean add(QuitOldPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuitOldPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuitOldPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public QuitOldPayListEntryInfo get(int index)
    {
        return(QuitOldPayListEntryInfo)getObject(index);
    }
    public QuitOldPayListEntryInfo get(Object key)
    {
        return(QuitOldPayListEntryInfo)getObject(key);
    }
    public void set(int index, QuitOldPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuitOldPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuitOldPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}