package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionPaperDefineCollection extends AbstractObjectCollection 
{
    public QuestionPaperDefineCollection()
    {
        super(QuestionPaperDefineInfo.class);
    }
    public boolean add(QuestionPaperDefineInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionPaperDefineCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionPaperDefineInfo item)
    {
        return removeObject(item);
    }
    public QuestionPaperDefineInfo get(int index)
    {
        return(QuestionPaperDefineInfo)getObject(index);
    }
    public QuestionPaperDefineInfo get(Object key)
    {
        return(QuestionPaperDefineInfo)getObject(key);
    }
    public void set(int index, QuestionPaperDefineInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionPaperDefineInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionPaperDefineInfo item)
    {
        return super.indexOf(item);
    }
}