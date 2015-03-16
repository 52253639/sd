package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LevelSetUpCollection extends AbstractObjectCollection 
{
    public LevelSetUpCollection()
    {
        super(LevelSetUpInfo.class);
    }
    public boolean add(LevelSetUpInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LevelSetUpCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LevelSetUpInfo item)
    {
        return removeObject(item);
    }
    public LevelSetUpInfo get(int index)
    {
        return(LevelSetUpInfo)getObject(index);
    }
    public LevelSetUpInfo get(Object key)
    {
        return(LevelSetUpInfo)getObject(key);
    }
    public void set(int index, LevelSetUpInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LevelSetUpInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LevelSetUpInfo item)
    {
        return super.indexOf(item);
    }
}