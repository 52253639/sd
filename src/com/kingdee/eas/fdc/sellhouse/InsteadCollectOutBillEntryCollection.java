package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsteadCollectOutBillEntryCollection extends AbstractObjectCollection 
{
    public InsteadCollectOutBillEntryCollection()
    {
        super(InsteadCollectOutBillEntryInfo.class);
    }
    public boolean add(InsteadCollectOutBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsteadCollectOutBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsteadCollectOutBillEntryInfo item)
    {
        return removeObject(item);
    }
    public InsteadCollectOutBillEntryInfo get(int index)
    {
        return(InsteadCollectOutBillEntryInfo)getObject(index);
    }
    public InsteadCollectOutBillEntryInfo get(Object key)
    {
        return(InsteadCollectOutBillEntryInfo)getObject(key);
    }
    public void set(int index, InsteadCollectOutBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InsteadCollectOutBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsteadCollectOutBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}