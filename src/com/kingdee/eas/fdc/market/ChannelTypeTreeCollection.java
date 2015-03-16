package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChannelTypeTreeCollection extends AbstractObjectCollection 
{
    public ChannelTypeTreeCollection()
    {
        super(ChannelTypeTreeInfo.class);
    }
    public boolean add(ChannelTypeTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChannelTypeTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChannelTypeTreeInfo item)
    {
        return removeObject(item);
    }
    public ChannelTypeTreeInfo get(int index)
    {
        return(ChannelTypeTreeInfo)getObject(index);
    }
    public ChannelTypeTreeInfo get(Object key)
    {
        return(ChannelTypeTreeInfo)getObject(key);
    }
    public void set(int index, ChannelTypeTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChannelTypeTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChannelTypeTreeInfo item)
    {
        return super.indexOf(item);
    }
}