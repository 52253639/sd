package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWFEntryCollection extends AbstractObjectCollection 
{
    public ContractWFEntryCollection()
    {
        super(ContractWFEntryInfo.class);
    }
    public boolean add(ContractWFEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWFEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWFEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractWFEntryInfo get(int index)
    {
        return(ContractWFEntryInfo)getObject(index);
    }
    public ContractWFEntryInfo get(Object key)
    {
        return(ContractWFEntryInfo)getObject(key);
    }
    public void set(int index, ContractWFEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWFEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWFEntryInfo item)
    {
        return super.indexOf(item);
    }
}