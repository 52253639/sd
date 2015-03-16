package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommerceChanceTrackCollection extends AbstractObjectCollection 
{
    public CommerceChanceTrackCollection()
    {
        super(CommerceChanceTrackInfo.class);
    }
    public boolean add(CommerceChanceTrackInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommerceChanceTrackCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommerceChanceTrackInfo item)
    {
        return removeObject(item);
    }
    public CommerceChanceTrackInfo get(int index)
    {
        return(CommerceChanceTrackInfo)getObject(index);
    }
    public CommerceChanceTrackInfo get(Object key)
    {
        return(CommerceChanceTrackInfo)getObject(key);
    }
    public void set(int index, CommerceChanceTrackInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommerceChanceTrackInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommerceChanceTrackInfo item)
    {
        return super.indexOf(item);
    }
}