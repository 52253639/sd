package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionPaperAnswerEntryCollection extends AbstractObjectCollection 
{
    public QuestionPaperAnswerEntryCollection()
    {
        super(QuestionPaperAnswerEntryInfo.class);
    }
    public boolean add(QuestionPaperAnswerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionPaperAnswerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionPaperAnswerEntryInfo item)
    {
        return removeObject(item);
    }
    public QuestionPaperAnswerEntryInfo get(int index)
    {
        return(QuestionPaperAnswerEntryInfo)getObject(index);
    }
    public QuestionPaperAnswerEntryInfo get(Object key)
    {
        return(QuestionPaperAnswerEntryInfo)getObject(key);
    }
    public void set(int index, QuestionPaperAnswerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionPaperAnswerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionPaperAnswerEntryInfo item)
    {
        return super.indexOf(item);
    }
}