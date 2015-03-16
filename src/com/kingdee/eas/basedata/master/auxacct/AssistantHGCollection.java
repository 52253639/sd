package com.kingdee.eas.basedata.master.auxacct;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AssistantHGCollection extends AbstractObjectCollection 
{
    public AssistantHGCollection()
    {
        super(AssistantHGInfo.class);
    }
    public boolean add(AssistantHGInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AssistantHGCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AssistantHGInfo item)
    {
        return removeObject(item);
    }
    public AssistantHGInfo get(int index)
    {
        return(AssistantHGInfo)getObject(index);
    }
    public AssistantHGInfo get(Object key)
    {
        return(AssistantHGInfo)getObject(key);
    }
    public void set(int index, AssistantHGInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AssistantHGInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AssistantHGInfo item)
    {
        return super.indexOf(item);
    }
}