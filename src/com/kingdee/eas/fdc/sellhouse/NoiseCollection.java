package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NoiseCollection extends AbstractObjectCollection 
{
    public NoiseCollection()
    {
        super(NoiseInfo.class);
    }
    public boolean add(NoiseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NoiseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NoiseInfo item)
    {
        return removeObject(item);
    }
    public NoiseInfo get(int index)
    {
        return(NoiseInfo)getObject(index);
    }
    public NoiseInfo get(Object key)
    {
        return(NoiseInfo)getObject(key);
    }
    public void set(int index, NoiseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NoiseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NoiseInfo item)
    {
        return super.indexOf(item);
    }
}