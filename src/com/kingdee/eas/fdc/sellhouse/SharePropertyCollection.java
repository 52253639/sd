package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SharePropertyCollection extends AbstractObjectCollection 
{
    public SharePropertyCollection()
    {
        super(SharePropertyInfo.class);
    }
    public boolean add(SharePropertyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SharePropertyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SharePropertyInfo item)
    {
        return removeObject(item);
    }
    public SharePropertyInfo get(int index)
    {
        return(SharePropertyInfo)getObject(index);
    }
    public SharePropertyInfo get(Object key)
    {
        return(SharePropertyInfo)getObject(key);
    }
    public void set(int index, SharePropertyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SharePropertyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SharePropertyInfo item)
    {
        return super.indexOf(item);
    }
}