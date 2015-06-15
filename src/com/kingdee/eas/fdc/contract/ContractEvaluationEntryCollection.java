package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractEvaluationEntryCollection extends AbstractObjectCollection 
{
    public ContractEvaluationEntryCollection()
    {
        super(ContractEvaluationEntryInfo.class);
    }
    public boolean add(ContractEvaluationEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractEvaluationEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractEvaluationEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractEvaluationEntryInfo get(int index)
    {
        return(ContractEvaluationEntryInfo)getObject(index);
    }
    public ContractEvaluationEntryInfo get(Object key)
    {
        return(ContractEvaluationEntryInfo)getObject(key);
    }
    public void set(int index, ContractEvaluationEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractEvaluationEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractEvaluationEntryInfo item)
    {
        return super.indexOf(item);
    }
}