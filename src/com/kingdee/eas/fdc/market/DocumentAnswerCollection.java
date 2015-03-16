package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DocumentAnswerCollection extends AbstractObjectCollection 
{
    public DocumentAnswerCollection()
    {
        super(DocumentAnswerInfo.class);
    }
    public boolean add(DocumentAnswerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DocumentAnswerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DocumentAnswerInfo item)
    {
        return removeObject(item);
    }
    public DocumentAnswerInfo get(int index)
    {
        return(DocumentAnswerInfo)getObject(index);
    }
    public DocumentAnswerInfo get(Object key)
    {
        return(DocumentAnswerInfo)getObject(key);
    }
    public void set(int index, DocumentAnswerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DocumentAnswerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DocumentAnswerInfo item)
    {
        return super.indexOf(item);
    }
}