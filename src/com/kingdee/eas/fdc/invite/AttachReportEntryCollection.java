package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachReportEntryCollection extends AbstractObjectCollection 
{
    public AttachReportEntryCollection()
    {
        super(AttachReportEntryInfo.class);
    }
    public boolean add(AttachReportEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachReportEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachReportEntryInfo item)
    {
        return removeObject(item);
    }
    public AttachReportEntryInfo get(int index)
    {
        return(AttachReportEntryInfo)getObject(index);
    }
    public AttachReportEntryInfo get(Object key)
    {
        return(AttachReportEntryInfo)getObject(key);
    }
    public void set(int index, AttachReportEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachReportEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachReportEntryInfo item)
    {
        return super.indexOf(item);
    }
}