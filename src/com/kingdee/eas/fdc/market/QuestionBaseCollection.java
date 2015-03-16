package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class QuestionBaseCollection extends AbstractObjectCollection 
{
    public QuestionBaseCollection()
    {
        super(QuestionBaseInfo.class);
    }
    public boolean add(QuestionBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(QuestionBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(QuestionBaseInfo item)
    {
        return removeObject(item);
    }
    public QuestionBaseInfo get(int index)
    {
        return(QuestionBaseInfo)getObject(index);
    }
    public QuestionBaseInfo get(Object key)
    {
        return(QuestionBaseInfo)getObject(key);
    }
    public void set(int index, QuestionBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(QuestionBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(QuestionBaseInfo item)
    {
        return super.indexOf(item);
    }
}