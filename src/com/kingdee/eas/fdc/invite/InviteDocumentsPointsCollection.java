package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteDocumentsPointsCollection extends AbstractObjectCollection 
{
    public InviteDocumentsPointsCollection()
    {
        super(InviteDocumentsPointsInfo.class);
    }
    public boolean add(InviteDocumentsPointsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteDocumentsPointsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteDocumentsPointsInfo item)
    {
        return removeObject(item);
    }
    public InviteDocumentsPointsInfo get(int index)
    {
        return(InviteDocumentsPointsInfo)getObject(index);
    }
    public InviteDocumentsPointsInfo get(Object key)
    {
        return(InviteDocumentsPointsInfo)getObject(key);
    }
    public void set(int index, InviteDocumentsPointsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteDocumentsPointsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteDocumentsPointsInfo item)
    {
        return super.indexOf(item);
    }
}