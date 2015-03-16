package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectArchivesEntryCollection extends AbstractObjectCollection 
{
    public ProjectArchivesEntryCollection()
    {
        super(ProjectArchivesEntryInfo.class);
    }
    public boolean add(ProjectArchivesEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectArchivesEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectArchivesEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectArchivesEntryInfo get(int index)
    {
        return(ProjectArchivesEntryInfo)getObject(index);
    }
    public ProjectArchivesEntryInfo get(Object key)
    {
        return(ProjectArchivesEntryInfo)getObject(key);
    }
    public void set(int index, ProjectArchivesEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectArchivesEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectArchivesEntryInfo item)
    {
        return super.indexOf(item);
    }
}