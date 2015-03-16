package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JoinDataEntryCollection extends AbstractObjectCollection 
{
    public JoinDataEntryCollection()
    {
        super(JoinDataEntryInfo.class);
    }
    public boolean add(JoinDataEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JoinDataEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JoinDataEntryInfo item)
    {
        return removeObject(item);
    }
    public JoinDataEntryInfo get(int index)
    {
        return(JoinDataEntryInfo)getObject(index);
    }
    public JoinDataEntryInfo get(Object key)
    {
        return(JoinDataEntryInfo)getObject(key);
    }
    public void set(int index, JoinDataEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JoinDataEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JoinDataEntryInfo item)
    {
        return super.indexOf(item);
    }
}