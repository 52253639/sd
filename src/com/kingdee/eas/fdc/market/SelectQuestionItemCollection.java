package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SelectQuestionItemCollection extends AbstractObjectCollection 
{
    public SelectQuestionItemCollection()
    {
        super(SelectQuestionItemInfo.class);
    }
    public boolean add(SelectQuestionItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SelectQuestionItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SelectQuestionItemInfo item)
    {
        return removeObject(item);
    }
    public SelectQuestionItemInfo get(int index)
    {
        return(SelectQuestionItemInfo)getObject(index);
    }
    public SelectQuestionItemInfo get(Object key)
    {
        return(SelectQuestionItemInfo)getObject(key);
    }
    public void set(int index, SelectQuestionItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SelectQuestionItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SelectQuestionItemInfo item)
    {
        return super.indexOf(item);
    }
}