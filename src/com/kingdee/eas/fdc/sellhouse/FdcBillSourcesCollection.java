package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FdcBillSourcesCollection extends AbstractObjectCollection 
{
    public FdcBillSourcesCollection()
    {
        super(FdcBillSourcesInfo.class);
    }
    public boolean add(FdcBillSourcesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FdcBillSourcesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FdcBillSourcesInfo item)
    {
        return removeObject(item);
    }
    public FdcBillSourcesInfo get(int index)
    {
        return(FdcBillSourcesInfo)getObject(index);
    }
    public FdcBillSourcesInfo get(Object key)
    {
        return(FdcBillSourcesInfo)getObject(key);
    }
    public void set(int index, FdcBillSourcesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FdcBillSourcesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FdcBillSourcesInfo item)
    {
        return super.indexOf(item);
    }
}