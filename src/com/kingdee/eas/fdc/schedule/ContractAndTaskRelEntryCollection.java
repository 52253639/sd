package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractAndTaskRelEntryCollection extends AbstractObjectCollection 
{
    public ContractAndTaskRelEntryCollection()
    {
        super(ContractAndTaskRelEntryInfo.class);
    }
    public boolean add(ContractAndTaskRelEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractAndTaskRelEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractAndTaskRelEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractAndTaskRelEntryInfo get(int index)
    {
        return(ContractAndTaskRelEntryInfo)getObject(index);
    }
    public ContractAndTaskRelEntryInfo get(Object key)
    {
        return(ContractAndTaskRelEntryInfo)getObject(key);
    }
    public void set(int index, ContractAndTaskRelEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractAndTaskRelEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractAndTaskRelEntryInfo item)
    {
        return super.indexOf(item);
    }
}