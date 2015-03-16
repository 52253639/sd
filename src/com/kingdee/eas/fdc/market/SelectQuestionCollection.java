package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SelectQuestionCollection extends AbstractObjectCollection 
{
    public SelectQuestionCollection()
    {
        super(SelectQuestionInfo.class);
    }
    public boolean add(SelectQuestionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SelectQuestionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SelectQuestionInfo item)
    {
        return removeObject(item);
    }
    public SelectQuestionInfo get(int index)
    {
        return(SelectQuestionInfo)getObject(index);
    }
    public SelectQuestionInfo get(Object key)
    {
        return(SelectQuestionInfo)getObject(key);
    }
    public void set(int index, SelectQuestionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SelectQuestionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SelectQuestionInfo item)
    {
        return super.indexOf(item);
    }
}