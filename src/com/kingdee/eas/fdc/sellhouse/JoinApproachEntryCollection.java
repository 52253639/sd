package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JoinApproachEntryCollection extends AbstractObjectCollection 
{
    public JoinApproachEntryCollection()
    {
        super(JoinApproachEntryInfo.class);
    }
    public boolean add(JoinApproachEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JoinApproachEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JoinApproachEntryInfo item)
    {
        return removeObject(item);
    }
    public JoinApproachEntryInfo get(int index)
    {
        return(JoinApproachEntryInfo)getObject(index);
    }
    public JoinApproachEntryInfo get(Object key)
    {
        return(JoinApproachEntryInfo)getObject(key);
    }
    public void set(int index, JoinApproachEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JoinApproachEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JoinApproachEntryInfo item)
    {
        return super.indexOf(item);
    }
}