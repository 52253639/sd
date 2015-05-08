package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSchTaskWithContractCollection extends AbstractObjectCollection 
{
    public FDCSchTaskWithContractCollection()
    {
        super(FDCSchTaskWithContractInfo.class);
    }
    public boolean add(FDCSchTaskWithContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSchTaskWithContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSchTaskWithContractInfo item)
    {
        return removeObject(item);
    }
    public FDCSchTaskWithContractInfo get(int index)
    {
        return(FDCSchTaskWithContractInfo)getObject(index);
    }
    public FDCSchTaskWithContractInfo get(Object key)
    {
        return(FDCSchTaskWithContractInfo)getObject(key);
    }
    public void set(int index, FDCSchTaskWithContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSchTaskWithContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSchTaskWithContractInfo item)
    {
        return super.indexOf(item);
    }
}