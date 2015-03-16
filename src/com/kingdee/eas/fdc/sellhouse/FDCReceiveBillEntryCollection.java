package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCReceiveBillEntryCollection extends AbstractObjectCollection 
{
    public FDCReceiveBillEntryCollection()
    {
        super(FDCReceiveBillEntryInfo.class);
    }
    public boolean add(FDCReceiveBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCReceiveBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCReceiveBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCReceiveBillEntryInfo get(int index)
    {
        return(FDCReceiveBillEntryInfo)getObject(index);
    }
    public FDCReceiveBillEntryInfo get(Object key)
    {
        return(FDCReceiveBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCReceiveBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCReceiveBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCReceiveBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}