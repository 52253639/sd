package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OpReportEntryBaseCollection extends AbstractObjectCollection 
{
    public OpReportEntryBaseCollection()
    {
        super(OpReportEntryBaseInfo.class);
    }
    public boolean add(OpReportEntryBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OpReportEntryBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OpReportEntryBaseInfo item)
    {
        return removeObject(item);
    }
    public OpReportEntryBaseInfo get(int index)
    {
        return(OpReportEntryBaseInfo)getObject(index);
    }
    public OpReportEntryBaseInfo get(Object key)
    {
        return(OpReportEntryBaseInfo)getObject(key);
    }
    public void set(int index, OpReportEntryBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OpReportEntryBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OpReportEntryBaseInfo item)
    {
        return super.indexOf(item);
    }
}