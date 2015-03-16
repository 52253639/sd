package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PropertyNoticeCollection extends AbstractObjectCollection 
{
    public PropertyNoticeCollection()
    {
        super(PropertyNoticeInfo.class);
    }
    public boolean add(PropertyNoticeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PropertyNoticeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PropertyNoticeInfo item)
    {
        return removeObject(item);
    }
    public PropertyNoticeInfo get(int index)
    {
        return(PropertyNoticeInfo)getObject(index);
    }
    public PropertyNoticeInfo get(Object key)
    {
        return(PropertyNoticeInfo)getObject(key);
    }
    public void set(int index, PropertyNoticeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PropertyNoticeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PropertyNoticeInfo item)
    {
        return super.indexOf(item);
    }
}