package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskEvaluationBillCollection extends AbstractObjectCollection 
{
    public TaskEvaluationBillCollection()
    {
        super(TaskEvaluationBillInfo.class);
    }
    public boolean add(TaskEvaluationBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskEvaluationBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskEvaluationBillInfo item)
    {
        return removeObject(item);
    }
    public TaskEvaluationBillInfo get(int index)
    {
        return(TaskEvaluationBillInfo)getObject(index);
    }
    public TaskEvaluationBillInfo get(Object key)
    {
        return(TaskEvaluationBillInfo)getObject(key);
    }
    public void set(int index, TaskEvaluationBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskEvaluationBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskEvaluationBillInfo item)
    {
        return super.indexOf(item);
    }
}