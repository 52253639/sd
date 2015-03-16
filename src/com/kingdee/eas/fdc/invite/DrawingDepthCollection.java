package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DrawingDepthCollection extends AbstractObjectCollection 
{
    public DrawingDepthCollection()
    {
        super(DrawingDepthInfo.class);
    }
    public boolean add(DrawingDepthInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DrawingDepthCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DrawingDepthInfo item)
    {
        return removeObject(item);
    }
    public DrawingDepthInfo get(int index)
    {
        return(DrawingDepthInfo)getObject(index);
    }
    public DrawingDepthInfo get(Object key)
    {
        return(DrawingDepthInfo)getObject(key);
    }
    public void set(int index, DrawingDepthInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DrawingDepthInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DrawingDepthInfo item)
    {
        return super.indexOf(item);
    }
}