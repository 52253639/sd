package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertAppraiseEntryCollection extends AbstractObjectCollection 
{
    public ExpertAppraiseEntryCollection()
    {
        super(ExpertAppraiseEntryInfo.class);
    }
    public boolean add(ExpertAppraiseEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertAppraiseEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertAppraiseEntryInfo item)
    {
        return removeObject(item);
    }
    public ExpertAppraiseEntryInfo get(int index)
    {
        return(ExpertAppraiseEntryInfo)getObject(index);
    }
    public ExpertAppraiseEntryInfo get(Object key)
    {
        return(ExpertAppraiseEntryInfo)getObject(key);
    }
    public void set(int index, ExpertAppraiseEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertAppraiseEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertAppraiseEntryInfo item)
    {
        return super.indexOf(item);
    }
}