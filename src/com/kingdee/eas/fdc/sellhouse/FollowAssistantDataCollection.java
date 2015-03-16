package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FollowAssistantDataCollection extends AbstractObjectCollection 
{
    public FollowAssistantDataCollection()
    {
        super(FollowAssistantDataInfo.class);
    }
    public boolean add(FollowAssistantDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FollowAssistantDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FollowAssistantDataInfo item)
    {
        return removeObject(item);
    }
    public FollowAssistantDataInfo get(int index)
    {
        return(FollowAssistantDataInfo)getObject(index);
    }
    public FollowAssistantDataInfo get(Object key)
    {
        return(FollowAssistantDataInfo)getObject(key);
    }
    public void set(int index, FollowAssistantDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FollowAssistantDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FollowAssistantDataInfo item)
    {
        return super.indexOf(item);
    }
}