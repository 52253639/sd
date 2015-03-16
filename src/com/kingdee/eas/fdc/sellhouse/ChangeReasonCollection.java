package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeReasonCollection extends AbstractObjectCollection 
{
    public ChangeReasonCollection()
    {
        super(ChangeReasonInfo.class);
    }
    public boolean add(ChangeReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeReasonInfo item)
    {
        return removeObject(item);
    }
    public ChangeReasonInfo get(int index)
    {
        return(ChangeReasonInfo)getObject(index);
    }
    public ChangeReasonInfo get(Object key)
    {
        return(ChangeReasonInfo)getObject(key);
    }
    public void set(int index, ChangeReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeReasonInfo item)
    {
        return super.indexOf(item);
    }
}