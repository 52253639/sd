package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EvaluationTypeEntryCollection extends AbstractObjectCollection 
{
    public EvaluationTypeEntryCollection()
    {
        super(EvaluationTypeEntryInfo.class);
    }
    public boolean add(EvaluationTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EvaluationTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EvaluationTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public EvaluationTypeEntryInfo get(int index)
    {
        return(EvaluationTypeEntryInfo)getObject(index);
    }
    public EvaluationTypeEntryInfo get(Object key)
    {
        return(EvaluationTypeEntryInfo)getObject(key);
    }
    public void set(int index, EvaluationTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EvaluationTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EvaluationTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}