package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHECusAssistantDataCollection extends AbstractObjectCollection 
{
    public SHECusAssistantDataCollection()
    {
        super(SHECusAssistantDataInfo.class);
    }
    public boolean add(SHECusAssistantDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHECusAssistantDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHECusAssistantDataInfo item)
    {
        return removeObject(item);
    }
    public SHECusAssistantDataInfo get(int index)
    {
        return(SHECusAssistantDataInfo)getObject(index);
    }
    public SHECusAssistantDataInfo get(Object key)
    {
        return(SHECusAssistantDataInfo)getObject(key);
    }
    public void set(int index, SHECusAssistantDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHECusAssistantDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHECusAssistantDataInfo item)
    {
        return super.indexOf(item);
    }
}