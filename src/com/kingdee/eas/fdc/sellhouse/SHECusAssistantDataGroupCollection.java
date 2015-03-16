package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHECusAssistantDataGroupCollection extends AbstractObjectCollection 
{
    public SHECusAssistantDataGroupCollection()
    {
        super(SHECusAssistantDataGroupInfo.class);
    }
    public boolean add(SHECusAssistantDataGroupInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHECusAssistantDataGroupCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHECusAssistantDataGroupInfo item)
    {
        return removeObject(item);
    }
    public SHECusAssistantDataGroupInfo get(int index)
    {
        return(SHECusAssistantDataGroupInfo)getObject(index);
    }
    public SHECusAssistantDataGroupInfo get(Object key)
    {
        return(SHECusAssistantDataGroupInfo)getObject(key);
    }
    public void set(int index, SHECusAssistantDataGroupInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHECusAssistantDataGroupInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHECusAssistantDataGroupInfo item)
    {
        return super.indexOf(item);
    }
}