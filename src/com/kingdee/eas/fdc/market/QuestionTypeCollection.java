package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionTypeCollection extends AbstractObjectCollection 
{
    public QuestionTypeCollection()
    {
        super(QuestionTypeInfo.class);
    }
    public boolean add(QuestionTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionTypeInfo item)
    {
        return removeObject(item);
    }
    public QuestionTypeInfo get(int index)
    {
        return(QuestionTypeInfo)getObject(index);
    }
    public QuestionTypeInfo get(Object key)
    {
        return(QuestionTypeInfo)getObject(key);
    }
    public void set(int index, QuestionTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionTypeInfo item)
    {
        return super.indexOf(item);
    }
}