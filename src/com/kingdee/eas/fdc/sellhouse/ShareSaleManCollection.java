package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShareSaleManCollection extends AbstractObjectCollection 
{
    public ShareSaleManCollection()
    {
        super(ShareSaleManInfo.class);
    }
    public boolean add(ShareSaleManInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShareSaleManCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShareSaleManInfo item)
    {
        return removeObject(item);
    }
    public ShareSaleManInfo get(int index)
    {
        return(ShareSaleManInfo)getObject(index);
    }
    public ShareSaleManInfo get(Object key)
    {
        return(ShareSaleManInfo)getObject(key);
    }
    public void set(int index, ShareSaleManInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShareSaleManInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShareSaleManInfo item)
    {
        return super.indexOf(item);
    }
}