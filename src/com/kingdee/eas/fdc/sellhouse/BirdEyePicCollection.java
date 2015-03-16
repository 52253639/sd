package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BirdEyePicCollection extends AbstractObjectCollection 
{
    public BirdEyePicCollection()
    {
        super(BirdEyePicInfo.class);
    }
    public boolean add(BirdEyePicInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BirdEyePicCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BirdEyePicInfo item)
    {
        return removeObject(item);
    }
    public BirdEyePicInfo get(int index)
    {
        return(BirdEyePicInfo)getObject(index);
    }
    public BirdEyePicInfo get(Object key)
    {
        return(BirdEyePicInfo)getObject(key);
    }
    public void set(int index, BirdEyePicInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BirdEyePicInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BirdEyePicInfo item)
    {
        return super.indexOf(item);
    }
}