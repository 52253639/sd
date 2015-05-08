package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SelfAndFinalEvaluationEntryCollection extends AbstractObjectCollection 
{
    public SelfAndFinalEvaluationEntryCollection()
    {
        super(SelfAndFinalEvaluationEntryInfo.class);
    }
    public boolean add(SelfAndFinalEvaluationEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SelfAndFinalEvaluationEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SelfAndFinalEvaluationEntryInfo item)
    {
        return removeObject(item);
    }
    public SelfAndFinalEvaluationEntryInfo get(int index)
    {
        return(SelfAndFinalEvaluationEntryInfo)getObject(index);
    }
    public SelfAndFinalEvaluationEntryInfo get(Object key)
    {
        return(SelfAndFinalEvaluationEntryInfo)getObject(key);
    }
    public void set(int index, SelfAndFinalEvaluationEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SelfAndFinalEvaluationEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SelfAndFinalEvaluationEntryInfo item)
    {
        return super.indexOf(item);
    }
}