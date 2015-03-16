package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FollowAssistantDataTypeCollection extends AbstractObjectCollection 
{
    public FollowAssistantDataTypeCollection()
    {
        super(FollowAssistantDataTypeInfo.class);
    }
    public boolean add(FollowAssistantDataTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FollowAssistantDataTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FollowAssistantDataTypeInfo item)
    {
        return removeObject(item);
    }
    public FollowAssistantDataTypeInfo get(int index)
    {
        return(FollowAssistantDataTypeInfo)getObject(index);
    }
    public FollowAssistantDataTypeInfo get(Object key)
    {
        return(FollowAssistantDataTypeInfo)getObject(key);
    }
    public void set(int index, FollowAssistantDataTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FollowAssistantDataTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FollowAssistantDataTypeInfo item)
    {
        return super.indexOf(item);
    }
}