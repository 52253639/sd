package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsteadCollectOutBillCollection extends AbstractObjectCollection 
{
    public InsteadCollectOutBillCollection()
    {
        super(InsteadCollectOutBillInfo.class);
    }
    public boolean add(InsteadCollectOutBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsteadCollectOutBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsteadCollectOutBillInfo item)
    {
        return removeObject(item);
    }
    public InsteadCollectOutBillInfo get(int index)
    {
        return(InsteadCollectOutBillInfo)getObject(index);
    }
    public InsteadCollectOutBillInfo get(Object key)
    {
        return(InsteadCollectOutBillInfo)getObject(key);
    }
    public void set(int index, InsteadCollectOutBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InsteadCollectOutBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsteadCollectOutBillInfo item)
    {
        return super.indexOf(item);
    }
}