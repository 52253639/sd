package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteDocumentsCollection extends AbstractObjectCollection 
{
    public InviteDocumentsCollection()
    {
        super(InviteDocumentsInfo.class);
    }
    public boolean add(InviteDocumentsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteDocumentsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteDocumentsInfo item)
    {
        return removeObject(item);
    }
    public InviteDocumentsInfo get(int index)
    {
        return(InviteDocumentsInfo)getObject(index);
    }
    public InviteDocumentsInfo get(Object key)
    {
        return(InviteDocumentsInfo)getObject(key);
    }
    public void set(int index, InviteDocumentsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteDocumentsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteDocumentsInfo item)
    {
        return super.indexOf(item);
    }
}