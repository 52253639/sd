package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class JudgeQuestionCollection extends AbstractObjectCollection 
{
    public JudgeQuestionCollection()
    {
        super(JudgeQuestionInfo.class);
    }
    public boolean add(JudgeQuestionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(JudgeQuestionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(JudgeQuestionInfo item)
    {
        return removeObject(item);
    }
    public JudgeQuestionInfo get(int index)
    {
        return(JudgeQuestionInfo)getObject(index);
    }
    public JudgeQuestionInfo get(Object key)
    {
        return(JudgeQuestionInfo)getObject(key);
    }
    public void set(int index, JudgeQuestionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(JudgeQuestionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(JudgeQuestionInfo item)
    {
        return super.indexOf(item);
    }
}