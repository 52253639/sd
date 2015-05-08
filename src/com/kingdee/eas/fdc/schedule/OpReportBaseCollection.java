package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OpReportBaseCollection extends AbstractObjectCollection 
{
    public OpReportBaseCollection()
    {
        super(OpReportBaseInfo.class);
    }
    public boolean add(OpReportBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OpReportBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OpReportBaseInfo item)
    {
        return removeObject(item);
    }
    public OpReportBaseInfo get(int index)
    {
        return(OpReportBaseInfo)getObject(index);
    }
    public OpReportBaseInfo get(Object key)
    {
        return(OpReportBaseInfo)getObject(key);
    }
    public void set(int index, OpReportBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OpReportBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OpReportBaseInfo item)
    {
        return super.indexOf(item);
    }
}