package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteDocumentsEntryCollection extends AbstractObjectCollection 
{
    public InviteDocumentsEntryCollection()
    {
        super(InviteDocumentsEntryInfo.class);
    }
    public boolean add(InviteDocumentsEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteDocumentsEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteDocumentsEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteDocumentsEntryInfo get(int index)
    {
        return(InviteDocumentsEntryInfo)getObject(index);
    }
    public InviteDocumentsEntryInfo get(Object key)
    {
        return(InviteDocumentsEntryInfo)getObject(key);
    }
    public void set(int index, InviteDocumentsEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteDocumentsEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteDocumentsEntryInfo item)
    {
        return super.indexOf(item);
    }
}