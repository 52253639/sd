package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChannelTypeCollection extends AbstractObjectCollection 
{
    public ChannelTypeCollection()
    {
        super(ChannelTypeInfo.class);
    }
    public boolean add(ChannelTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChannelTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChannelTypeInfo item)
    {
        return removeObject(item);
    }
    public ChannelTypeInfo get(int index)
    {
        return(ChannelTypeInfo)getObject(index);
    }
    public ChannelTypeInfo get(Object key)
    {
        return(ChannelTypeInfo)getObject(key);
    }
    public void set(int index, ChannelTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChannelTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChannelTypeInfo item)
    {
        return super.indexOf(item);
    }
}