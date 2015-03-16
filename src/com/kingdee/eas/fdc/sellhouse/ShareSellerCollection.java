package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShareSellerCollection extends AbstractObjectCollection 
{
    public ShareSellerCollection()
    {
        super(ShareSellerInfo.class);
    }
    public boolean add(ShareSellerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShareSellerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShareSellerInfo item)
    {
        return removeObject(item);
    }
    public ShareSellerInfo get(int index)
    {
        return(ShareSellerInfo)getObject(index);
    }
    public ShareSellerInfo get(Object key)
    {
        return(ShareSellerInfo)getObject(key);
    }
    public void set(int index, ShareSellerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShareSellerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShareSellerInfo item)
    {
        return super.indexOf(item);
    }
}