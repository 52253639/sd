package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DocumentOptionCollection extends AbstractObjectCollection 
{
    public DocumentOptionCollection()
    {
        super(DocumentOptionInfo.class);
    }
    public boolean add(DocumentOptionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DocumentOptionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DocumentOptionInfo item)
    {
        return removeObject(item);
    }
    public DocumentOptionInfo get(int index)
    {
        return(DocumentOptionInfo)getObject(index);
    }
    public DocumentOptionInfo get(Object key)
    {
        return(DocumentOptionInfo)getObject(key);
    }
    public void set(int index, DocumentOptionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DocumentOptionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DocumentOptionInfo item)
    {
        return super.indexOf(item);
    }
}