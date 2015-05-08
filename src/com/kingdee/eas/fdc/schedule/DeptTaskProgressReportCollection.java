package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeptTaskProgressReportCollection extends AbstractObjectCollection 
{
    public DeptTaskProgressReportCollection()
    {
        super(DeptTaskProgressReportInfo.class);
    }
    public boolean add(DeptTaskProgressReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeptTaskProgressReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeptTaskProgressReportInfo item)
    {
        return removeObject(item);
    }
    public DeptTaskProgressReportInfo get(int index)
    {
        return(DeptTaskProgressReportInfo)getObject(index);
    }
    public DeptTaskProgressReportInfo get(Object key)
    {
        return(DeptTaskProgressReportInfo)getObject(key);
    }
    public void set(int index, DeptTaskProgressReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeptTaskProgressReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeptTaskProgressReportInfo item)
    {
        return super.indexOf(item);
    }
}