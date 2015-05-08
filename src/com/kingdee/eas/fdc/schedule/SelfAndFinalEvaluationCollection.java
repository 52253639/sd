package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SelfAndFinalEvaluationCollection extends AbstractObjectCollection 
{
    public SelfAndFinalEvaluationCollection()
    {
        super(SelfAndFinalEvaluationInfo.class);
    }
    public boolean add(SelfAndFinalEvaluationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SelfAndFinalEvaluationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SelfAndFinalEvaluationInfo item)
    {
        return removeObject(item);
    }
    public SelfAndFinalEvaluationInfo get(int index)
    {
        return(SelfAndFinalEvaluationInfo)getObject(index);
    }
    public SelfAndFinalEvaluationInfo get(Object key)
    {
        return(SelfAndFinalEvaluationInfo)getObject(key);
    }
    public void set(int index, SelfAndFinalEvaluationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SelfAndFinalEvaluationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SelfAndFinalEvaluationInfo item)
    {
        return super.indexOf(item);
    }
}