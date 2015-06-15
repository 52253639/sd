package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationTypeCollection extends AbstractObjectCollection 
{
    public EvaluationTypeCollection()
    {
        super(EvaluationTypeInfo.class);
    }
    public boolean add(EvaluationTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationTypeInfo item)
    {
        return removeObject(item);
    }
    public EvaluationTypeInfo get(int index)
    {
        return(EvaluationTypeInfo)getObject(index);
    }
    public EvaluationTypeInfo get(Object key)
    {
        return(EvaluationTypeInfo)getObject(key);
    }
    public void set(int index, EvaluationTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationTypeInfo item)
    {
        return super.indexOf(item);
    }
}