package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JoinDoSchemeCollection extends AbstractObjectCollection 
{
    public JoinDoSchemeCollection()
    {
        super(JoinDoSchemeInfo.class);
    }
    public boolean add(JoinDoSchemeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JoinDoSchemeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JoinDoSchemeInfo item)
    {
        return removeObject(item);
    }
    public JoinDoSchemeInfo get(int index)
    {
        return(JoinDoSchemeInfo)getObject(index);
    }
    public JoinDoSchemeInfo get(Object key)
    {
        return(JoinDoSchemeInfo)getObject(key);
    }
    public void set(int index, JoinDoSchemeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JoinDoSchemeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JoinDoSchemeInfo item)
    {
        return super.indexOf(item);
    }
}