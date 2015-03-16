package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AskQuestionCollection extends AbstractObjectCollection 
{
    public AskQuestionCollection()
    {
        super(AskQuestionInfo.class);
    }
    public boolean add(AskQuestionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AskQuestionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AskQuestionInfo item)
    {
        return removeObject(item);
    }
    public AskQuestionInfo get(int index)
    {
        return(AskQuestionInfo)getObject(index);
    }
    public AskQuestionInfo get(Object key)
    {
        return(AskQuestionInfo)getObject(key);
    }
    public void set(int index, AskQuestionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AskQuestionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AskQuestionInfo item)
    {
        return super.indexOf(item);
    }
}