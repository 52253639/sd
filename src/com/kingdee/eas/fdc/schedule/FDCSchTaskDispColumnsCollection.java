package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSchTaskDispColumnsCollection extends AbstractObjectCollection 
{
    public FDCSchTaskDispColumnsCollection()
    {
        super(FDCSchTaskDispColumnsInfo.class);
    }
    public boolean add(FDCSchTaskDispColumnsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSchTaskDispColumnsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSchTaskDispColumnsInfo item)
    {
        return removeObject(item);
    }
    public FDCSchTaskDispColumnsInfo get(int index)
    {
        return(FDCSchTaskDispColumnsInfo)getObject(index);
    }
    public FDCSchTaskDispColumnsInfo get(Object key)
    {
        return(FDCSchTaskDispColumnsInfo)getObject(key);
    }
    public void set(int index, FDCSchTaskDispColumnsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSchTaskDispColumnsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSchTaskDispColumnsInfo item)
    {
        return super.indexOf(item);
    }
}