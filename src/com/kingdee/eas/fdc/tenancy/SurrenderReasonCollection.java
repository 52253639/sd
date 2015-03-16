package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SurrenderReasonCollection extends AbstractObjectCollection 
{
    public SurrenderReasonCollection()
    {
        super(SurrenderReasonInfo.class);
    }
    public boolean add(SurrenderReasonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SurrenderReasonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SurrenderReasonInfo item)
    {
        return removeObject(item);
    }
    public SurrenderReasonInfo get(int index)
    {
        return(SurrenderReasonInfo)getObject(index);
    }
    public SurrenderReasonInfo get(Object key)
    {
        return(SurrenderReasonInfo)getObject(key);
    }
    public void set(int index, SurrenderReasonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SurrenderReasonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SurrenderReasonInfo item)
    {
        return super.indexOf(item);
    }
}