package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PartAMainContractCollection extends AbstractObjectCollection 
{
    public PartAMainContractCollection()
    {
        super(PartAMainContractInfo.class);
    }
    public boolean add(PartAMainContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PartAMainContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PartAMainContractInfo item)
    {
        return removeObject(item);
    }
    public PartAMainContractInfo get(int index)
    {
        return(PartAMainContractInfo)getObject(index);
    }
    public PartAMainContractInfo get(Object key)
    {
        return(PartAMainContractInfo)getObject(key);
    }
    public void set(int index, PartAMainContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PartAMainContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PartAMainContractInfo item)
    {
        return super.indexOf(item);
    }
}