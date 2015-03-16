package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteTypeCollection extends AbstractObjectCollection 
{
    public InviteTypeCollection()
    {
        super(InviteTypeInfo.class);
    }
    public boolean add(InviteTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteTypeInfo item)
    {
        return removeObject(item);
    }
    public InviteTypeInfo get(int index)
    {
        return(InviteTypeInfo)getObject(index);
    }
    public InviteTypeInfo get(Object key)
    {
        return(InviteTypeInfo)getObject(key);
    }
    public void set(int index, InviteTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteTypeInfo item)
    {
        return super.indexOf(item);
    }
}