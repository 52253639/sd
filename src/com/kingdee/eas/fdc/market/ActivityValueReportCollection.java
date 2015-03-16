package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ActivityValueReportCollection extends AbstractObjectCollection 
{
    public ActivityValueReportCollection()
    {
        super(ActivityValueReportInfo.class);
    }
    public boolean add(ActivityValueReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ActivityValueReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ActivityValueReportInfo item)
    {
        return removeObject(item);
    }
    public ActivityValueReportInfo get(int index)
    {
        return(ActivityValueReportInfo)getObject(index);
    }
    public ActivityValueReportInfo get(Object key)
    {
        return(ActivityValueReportInfo)getObject(key);
    }
    public void set(int index, ActivityValueReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ActivityValueReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ActivityValueReportInfo item)
    {
        return super.indexOf(item);
    }
}