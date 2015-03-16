package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarktQuaLevelCollection extends AbstractObjectCollection 
{
    public MarktQuaLevelCollection()
    {
        super(MarktQuaLevelInfo.class);
    }
    public boolean add(MarktQuaLevelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarktQuaLevelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarktQuaLevelInfo item)
    {
        return removeObject(item);
    }
    public MarktQuaLevelInfo get(int index)
    {
        return(MarktQuaLevelInfo)getObject(index);
    }
    public MarktQuaLevelInfo get(Object key)
    {
        return(MarktQuaLevelInfo)getObject(key);
    }
    public void set(int index, MarktQuaLevelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarktQuaLevelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarktQuaLevelInfo item)
    {
        return super.indexOf(item);
    }
}