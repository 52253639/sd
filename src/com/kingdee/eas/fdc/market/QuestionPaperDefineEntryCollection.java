package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionPaperDefineEntryCollection extends AbstractObjectCollection 
{
    public QuestionPaperDefineEntryCollection()
    {
        super(QuestionPaperDefineEntryInfo.class);
    }
    public boolean add(QuestionPaperDefineEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionPaperDefineEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionPaperDefineEntryInfo item)
    {
        return removeObject(item);
    }
    public QuestionPaperDefineEntryInfo get(int index)
    {
        return(QuestionPaperDefineEntryInfo)getObject(index);
    }
    public QuestionPaperDefineEntryInfo get(Object key)
    {
        return(QuestionPaperDefineEntryInfo)getObject(key);
    }
    public void set(int index, QuestionPaperDefineEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionPaperDefineEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionPaperDefineEntryInfo item)
    {
        return super.indexOf(item);
    }
}