package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgeQuestionAnswerCollection extends AbstractObjectCollection 
{
    public JudgeQuestionAnswerCollection()
    {
        super(JudgeQuestionAnswerInfo.class);
    }
    public boolean add(JudgeQuestionAnswerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgeQuestionAnswerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgeQuestionAnswerInfo item)
    {
        return removeObject(item);
    }
    public JudgeQuestionAnswerInfo get(int index)
    {
        return(JudgeQuestionAnswerInfo)getObject(index);
    }
    public JudgeQuestionAnswerInfo get(Object key)
    {
        return(JudgeQuestionAnswerInfo)getObject(key);
    }
    public void set(int index, JudgeQuestionAnswerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgeQuestionAnswerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgeQuestionAnswerInfo item)
    {
        return super.indexOf(item);
    }
}