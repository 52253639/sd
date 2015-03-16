package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MortagageControlCollection extends AbstractObjectCollection 
{
    public MortagageControlCollection()
    {
        super(MortagageControlInfo.class);
    }
    public boolean add(MortagageControlInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MortagageControlCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MortagageControlInfo item)
    {
        return removeObject(item);
    }
    public MortagageControlInfo get(int index)
    {
        return(MortagageControlInfo)getObject(index);
    }
    public MortagageControlInfo get(Object key)
    {
        return(MortagageControlInfo)getObject(key);
    }
    public void set(int index, MortagageControlInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MortagageControlInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MortagageControlInfo item)
    {
        return super.indexOf(item);
    }
}