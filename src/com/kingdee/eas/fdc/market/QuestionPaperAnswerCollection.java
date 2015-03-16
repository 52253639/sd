package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionPaperAnswerCollection extends AbstractObjectCollection 
{
    public QuestionPaperAnswerCollection()
    {
        super(QuestionPaperAnswerInfo.class);
    }
    public boolean add(QuestionPaperAnswerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionPaperAnswerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionPaperAnswerInfo item)
    {
        return removeObject(item);
    }
    public QuestionPaperAnswerInfo get(int index)
    {
        return(QuestionPaperAnswerInfo)getObject(index);
    }
    public QuestionPaperAnswerInfo get(Object key)
    {
        return(QuestionPaperAnswerInfo)getObject(key);
    }
    public void set(int index, QuestionPaperAnswerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionPaperAnswerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionPaperAnswerInfo item)
    {
        return super.indexOf(item);
    }
}