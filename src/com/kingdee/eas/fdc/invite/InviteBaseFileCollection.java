package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteBaseFileCollection extends AbstractObjectCollection 
{
    public InviteBaseFileCollection()
    {
        super(InviteBaseFileInfo.class);
    }
    public boolean add(InviteBaseFileInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteBaseFileCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteBaseFileInfo item)
    {
        return removeObject(item);
    }
    public InviteBaseFileInfo get(int index)
    {
        return(InviteBaseFileInfo)getObject(index);
    }
    public InviteBaseFileInfo get(Object key)
    {
        return(InviteBaseFileInfo)getObject(key);
    }
    public void set(int index, InviteBaseFileInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteBaseFileInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteBaseFileInfo item)
    {
        return super.indexOf(item);
    }
}