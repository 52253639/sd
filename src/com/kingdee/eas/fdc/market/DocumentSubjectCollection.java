package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DocumentSubjectCollection extends AbstractObjectCollection 
{
    public DocumentSubjectCollection()
    {
        super(DocumentSubjectInfo.class);
    }
    public boolean add(DocumentSubjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DocumentSubjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DocumentSubjectInfo item)
    {
        return removeObject(item);
    }
    public DocumentSubjectInfo get(int index)
    {
        return(DocumentSubjectInfo)getObject(index);
    }
    public DocumentSubjectInfo get(Object key)
    {
        return(DocumentSubjectInfo)getObject(key);
    }
    public void set(int index, DocumentSubjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DocumentSubjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DocumentSubjectInfo item)
    {
        return super.indexOf(item);
    }
}