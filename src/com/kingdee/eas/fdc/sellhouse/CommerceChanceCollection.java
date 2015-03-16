package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommerceChanceCollection extends AbstractObjectCollection 
{
    public CommerceChanceCollection()
    {
        super(CommerceChanceInfo.class);
    }
    public boolean add(CommerceChanceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommerceChanceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommerceChanceInfo item)
    {
        return removeObject(item);
    }
    public CommerceChanceInfo get(int index)
    {
        return(CommerceChanceInfo)getObject(index);
    }
    public CommerceChanceInfo get(Object key)
    {
        return(CommerceChanceInfo)getObject(key);
    }
    public void set(int index, CommerceChanceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommerceChanceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommerceChanceInfo item)
    {
        return super.indexOf(item);
    }
}