package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BirdEyeCollection extends AbstractObjectCollection 
{
    public BirdEyeCollection()
    {
        super(BirdEyeInfo.class);
    }
    public boolean add(BirdEyeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BirdEyeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BirdEyeInfo item)
    {
        return removeObject(item);
    }
    public BirdEyeInfo get(int index)
    {
        return(BirdEyeInfo)getObject(index);
    }
    public BirdEyeInfo get(Object key)
    {
        return(BirdEyeInfo)getObject(key);
    }
    public void set(int index, BirdEyeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BirdEyeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BirdEyeInfo item)
    {
        return super.indexOf(item);
    }
}