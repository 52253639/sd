package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCReceiveBillCollection extends AbstractObjectCollection 
{
    public FDCReceiveBillCollection()
    {
        super(FDCReceiveBillInfo.class);
    }
    public boolean add(FDCReceiveBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCReceiveBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCReceiveBillInfo item)
    {
        return removeObject(item);
    }
    public FDCReceiveBillInfo get(int index)
    {
        return(FDCReceiveBillInfo)getObject(index);
    }
    public FDCReceiveBillInfo get(Object key)
    {
        return(FDCReceiveBillInfo)getObject(key);
    }
    public void set(int index, FDCReceiveBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCReceiveBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCReceiveBillInfo item)
    {
        return super.indexOf(item);
    }
}