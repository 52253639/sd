package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TrackRecordCollection extends AbstractObjectCollection 
{
    public TrackRecordCollection()
    {
        super(TrackRecordInfo.class);
    }
    public boolean add(TrackRecordInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TrackRecordCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TrackRecordInfo item)
    {
        return removeObject(item);
    }
    public TrackRecordInfo get(int index)
    {
        return(TrackRecordInfo)getObject(index);
    }
    public TrackRecordInfo get(Object key)
    {
        return(TrackRecordInfo)getObject(key);
    }
    public void set(int index, TrackRecordInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TrackRecordInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TrackRecordInfo item)
    {
        return super.indexOf(item);
    }
}