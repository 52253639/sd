package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskEvaluationCollection extends AbstractObjectCollection 
{
    public TaskEvaluationCollection()
    {
        super(TaskEvaluationInfo.class);
    }
    public boolean add(TaskEvaluationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskEvaluationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskEvaluationInfo item)
    {
        return removeObject(item);
    }
    public TaskEvaluationInfo get(int index)
    {
        return(TaskEvaluationInfo)getObject(index);
    }
    public TaskEvaluationInfo get(Object key)
    {
        return(TaskEvaluationInfo)getObject(key);
    }
    public void set(int index, TaskEvaluationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskEvaluationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskEvaluationInfo item)
    {
        return super.indexOf(item);
    }
}