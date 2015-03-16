package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommerceChanceAssistantCollection extends AbstractObjectCollection 
{
    public CommerceChanceAssistantCollection()
    {
        super(CommerceChanceAssistantInfo.class);
    }
    public boolean add(CommerceChanceAssistantInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommerceChanceAssistantCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommerceChanceAssistantInfo item)
    {
        return removeObject(item);
    }
    public CommerceChanceAssistantInfo get(int index)
    {
        return(CommerceChanceAssistantInfo)getObject(index);
    }
    public CommerceChanceAssistantInfo get(Object key)
    {
        return(CommerceChanceAssistantInfo)getObject(key);
    }
    public void set(int index, CommerceChanceAssistantInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommerceChanceAssistantInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommerceChanceAssistantInfo item)
    {
        return super.indexOf(item);
    }
}