package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DocumentItemCollection extends AbstractObjectCollection 
{
    public DocumentItemCollection()
    {
        super(DocumentItemInfo.class);
    }
    public boolean add(DocumentItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DocumentItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DocumentItemInfo item)
    {
        return removeObject(item);
    }
    public DocumentItemInfo get(int index)
    {
        return(DocumentItemInfo)getObject(index);
    }
    public DocumentItemInfo get(Object key)
    {
        return(DocumentItemInfo)getObject(key);
    }
    public void set(int index, DocumentItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DocumentItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DocumentItemInfo item)
    {
        return super.indexOf(item);
    }
}