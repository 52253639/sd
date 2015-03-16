package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachReportCollection extends AbstractObjectCollection 
{
    public AttachReportCollection()
    {
        super(AttachReportInfo.class);
    }
    public boolean add(AttachReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachReportInfo item)
    {
        return removeObject(item);
    }
    public AttachReportInfo get(int index)
    {
        return(AttachReportInfo)getObject(index);
    }
    public AttachReportInfo get(Object key)
    {
        return(AttachReportInfo)getObject(key);
    }
    public void set(int index, AttachReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachReportInfo item)
    {
        return super.indexOf(item);
    }
}