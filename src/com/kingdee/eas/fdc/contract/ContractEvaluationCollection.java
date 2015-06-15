package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractEvaluationCollection extends AbstractObjectCollection 
{
    public ContractEvaluationCollection()
    {
        super(ContractEvaluationInfo.class);
    }
    public boolean add(ContractEvaluationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractEvaluationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractEvaluationInfo item)
    {
        return removeObject(item);
    }
    public ContractEvaluationInfo get(int index)
    {
        return(ContractEvaluationInfo)getObject(index);
    }
    public ContractEvaluationInfo get(Object key)
    {
        return(ContractEvaluationInfo)getObject(key);
    }
    public void set(int index, ContractEvaluationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractEvaluationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractEvaluationInfo item)
    {
        return super.indexOf(item);
    }
}