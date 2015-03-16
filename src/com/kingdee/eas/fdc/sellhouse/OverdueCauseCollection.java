package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OverdueCauseCollection extends AbstractObjectCollection 
{
    public OverdueCauseCollection()
    {
        super(OverdueCauseInfo.class);
    }
    public boolean add(OverdueCauseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OverdueCauseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OverdueCauseInfo item)
    {
        return removeObject(item);
    }
    public OverdueCauseInfo get(int index)
    {
        return(OverdueCauseInfo)getObject(index);
    }
    public OverdueCauseInfo get(Object key)
    {
        return(OverdueCauseInfo)getObject(key);
    }
    public void set(int index, OverdueCauseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OverdueCauseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OverdueCauseInfo item)
    {
        return super.indexOf(item);
    }
}