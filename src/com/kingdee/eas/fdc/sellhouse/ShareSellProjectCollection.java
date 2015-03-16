package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShareSellProjectCollection extends AbstractObjectCollection 
{
    public ShareSellProjectCollection()
    {
        super(ShareSellProjectInfo.class);
    }
    public boolean add(ShareSellProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShareSellProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShareSellProjectInfo item)
    {
        return removeObject(item);
    }
    public ShareSellProjectInfo get(int index)
    {
        return(ShareSellProjectInfo)getObject(index);
    }
    public ShareSellProjectInfo get(Object key)
    {
        return(ShareSellProjectInfo)getObject(key);
    }
    public void set(int index, ShareSellProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShareSellProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShareSellProjectInfo item)
    {
        return super.indexOf(item);
    }
}