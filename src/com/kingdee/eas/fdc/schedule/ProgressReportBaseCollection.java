package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgressReportBaseCollection extends AbstractObjectCollection 
{
    public ProgressReportBaseCollection()
    {
        super(ProgressReportBaseInfo.class);
    }
    public boolean add(ProgressReportBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgressReportBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgressReportBaseInfo item)
    {
        return removeObject(item);
    }
    public ProgressReportBaseInfo get(int index)
    {
        return(ProgressReportBaseInfo)getObject(index);
    }
    public ProgressReportBaseInfo get(Object key)
    {
        return(ProgressReportBaseInfo)getObject(key);
    }
    public void set(int index, ProgressReportBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgressReportBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgressReportBaseInfo item)
    {
        return super.indexOf(item);
    }
}