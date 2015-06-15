package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationResultCollection extends AbstractObjectCollection 
{
    public EvaluationResultCollection()
    {
        super(EvaluationResultInfo.class);
    }
    public boolean add(EvaluationResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationResultInfo item)
    {
        return removeObject(item);
    }
    public EvaluationResultInfo get(int index)
    {
        return(EvaluationResultInfo)getObject(index);
    }
    public EvaluationResultInfo get(Object key)
    {
        return(EvaluationResultInfo)getObject(key);
    }
    public void set(int index, EvaluationResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationResultInfo item)
    {
        return super.indexOf(item);
    }
}