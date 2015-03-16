package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DocumentCollection extends AbstractObjectCollection 
{
    public DocumentCollection()
    {
        super(DocumentInfo.class);
    }
    public boolean add(DocumentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DocumentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DocumentInfo item)
    {
        return removeObject(item);
    }
    public DocumentInfo get(int index)
    {
        return(DocumentInfo)getObject(index);
    }
    public DocumentInfo get(Object key)
    {
        return(DocumentInfo)getObject(key);
    }
    public void set(int index, DocumentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DocumentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DocumentInfo item)
    {
        return super.indexOf(item);
    }
}