package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestSplitEntryCollection extends AbstractObjectCollection 
{
    public PayRequestSplitEntryCollection()
    {
        super(PayRequestSplitEntryInfo.class);
    }
    public boolean add(PayRequestSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public PayRequestSplitEntryInfo get(int index)
    {
        return(PayRequestSplitEntryInfo)getObject(index);
    }
    public PayRequestSplitEntryInfo get(Object key)
    {
        return(PayRequestSplitEntryInfo)getObject(key);
    }
    public void set(int index, PayRequestSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}