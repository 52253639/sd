package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuaLevelCollection extends AbstractObjectCollection 
{
    public QuaLevelCollection()
    {
        super(QuaLevelInfo.class);
    }
    public boolean add(QuaLevelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuaLevelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuaLevelInfo item)
    {
        return removeObject(item);
    }
    public QuaLevelInfo get(int index)
    {
        return(QuaLevelInfo)getObject(index);
    }
    public QuaLevelInfo get(Object key)
    {
        return(QuaLevelInfo)getObject(key);
    }
    public void set(int index, QuaLevelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuaLevelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuaLevelInfo item)
    {
        return super.indexOf(item);
    }
}