package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ArchivesEntryCollection extends AbstractObjectCollection 
{
    public ArchivesEntryCollection()
    {
        super(ArchivesEntryInfo.class);
    }
    public boolean add(ArchivesEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ArchivesEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ArchivesEntryInfo item)
    {
        return removeObject(item);
    }
    public ArchivesEntryInfo get(int index)
    {
        return(ArchivesEntryInfo)getObject(index);
    }
    public ArchivesEntryInfo get(Object key)
    {
        return(ArchivesEntryInfo)getObject(key);
    }
    public void set(int index, ArchivesEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ArchivesEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ArchivesEntryInfo item)
    {
        return super.indexOf(item);
    }
}