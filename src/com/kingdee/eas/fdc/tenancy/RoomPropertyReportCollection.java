package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyReportCollection extends AbstractObjectCollection 
{
    public RoomPropertyReportCollection()
    {
        super(RoomPropertyReportInfo.class);
    }
    public boolean add(RoomPropertyReportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyReportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyReportInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyReportInfo get(int index)
    {
        return(RoomPropertyReportInfo)getObject(index);
    }
    public RoomPropertyReportInfo get(Object key)
    {
        return(RoomPropertyReportInfo)getObject(key);
    }
    public void set(int index, RoomPropertyReportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyReportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyReportInfo item)
    {
        return super.indexOf(item);
    }
}