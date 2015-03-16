package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FillBlankQuestionCollection extends AbstractObjectCollection 
{
    public FillBlankQuestionCollection()
    {
        super(FillBlankQuestionInfo.class);
    }
    public boolean add(FillBlankQuestionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FillBlankQuestionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FillBlankQuestionInfo item)
    {
        return removeObject(item);
    }
    public FillBlankQuestionInfo get(int index)
    {
        return(FillBlankQuestionInfo)getObject(index);
    }
    public FillBlankQuestionInfo get(Object key)
    {
        return(FillBlankQuestionInfo)getObject(key);
    }
    public void set(int index, FillBlankQuestionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FillBlankQuestionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FillBlankQuestionInfo item)
    {
        return super.indexOf(item);
    }
}