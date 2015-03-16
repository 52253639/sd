package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class UpdateDataCollection extends AbstractObjectCollection 
{
    public UpdateDataCollection()
    {
        super(UpdateDataInfo.class);
    }
    public boolean add(UpdateDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(UpdateDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(UpdateDataInfo item)
    {
        return removeObject(item);
    }
    public UpdateDataInfo get(int index)
    {
        return(UpdateDataInfo)getObject(index);
    }
    public UpdateDataInfo get(Object key)
    {
        return(UpdateDataInfo)getObject(key);
    }
    public void set(int index, UpdateDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(UpdateDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(UpdateDataInfo item)
    {
        return super.indexOf(item);
    }
}