package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MediaCollection extends AbstractObjectCollection 
{
    public MediaCollection()
    {
        super(MediaInfo.class);
    }
    public boolean add(MediaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MediaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MediaInfo item)
    {
        return removeObject(item);
    }
    public MediaInfo get(int index)
    {
        return(MediaInfo)getObject(index);
    }
    public MediaInfo get(Object key)
    {
        return(MediaInfo)getObject(key);
    }
    public void set(int index, MediaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MediaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MediaInfo item)
    {
        return super.indexOf(item);
    }
}