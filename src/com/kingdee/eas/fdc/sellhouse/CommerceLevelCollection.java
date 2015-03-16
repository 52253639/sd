package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommerceLevelCollection extends AbstractObjectCollection 
{
    public CommerceLevelCollection()
    {
        super(CommerceLevelInfo.class);
    }
    public boolean add(CommerceLevelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommerceLevelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommerceLevelInfo item)
    {
        return removeObject(item);
    }
    public CommerceLevelInfo get(int index)
    {
        return(CommerceLevelInfo)getObject(index);
    }
    public CommerceLevelInfo get(Object key)
    {
        return(CommerceLevelInfo)getObject(key);
    }
    public void set(int index, CommerceLevelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommerceLevelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommerceLevelInfo item)
    {
        return super.indexOf(item);
    }
}