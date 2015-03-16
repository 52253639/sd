package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHERevMapCollection extends AbstractObjectCollection 
{
    public SHERevMapCollection()
    {
        super(SHERevMapInfo.class);
    }
    public boolean add(SHERevMapInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHERevMapCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHERevMapInfo item)
    {
        return removeObject(item);
    }
    public SHERevMapInfo get(int index)
    {
        return(SHERevMapInfo)getObject(index);
    }
    public SHERevMapInfo get(Object key)
    {
        return(SHERevMapInfo)getObject(key);
    }
    public void set(int index, SHERevMapInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHERevMapInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHERevMapInfo item)
    {
        return super.indexOf(item);
    }
}