package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestSplitCollection extends AbstractObjectCollection 
{
    public PayRequestSplitCollection()
    {
        super(PayRequestSplitInfo.class);
    }
    public boolean add(PayRequestSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestSplitInfo item)
    {
        return removeObject(item);
    }
    public PayRequestSplitInfo get(int index)
    {
        return(PayRequestSplitInfo)getObject(index);
    }
    public PayRequestSplitInfo get(Object key)
    {
        return(PayRequestSplitInfo)getObject(key);
    }
    public void set(int index, PayRequestSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestSplitInfo item)
    {
        return super.indexOf(item);
    }
}