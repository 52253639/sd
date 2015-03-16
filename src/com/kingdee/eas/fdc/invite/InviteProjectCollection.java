package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteProjectCollection extends AbstractObjectCollection 
{
    public InviteProjectCollection()
    {
        super(InviteProjectInfo.class);
    }
    public boolean add(InviteProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteProjectInfo item)
    {
        return removeObject(item);
    }
    public InviteProjectInfo get(int index)
    {
        return(InviteProjectInfo)getObject(index);
    }
    public InviteProjectInfo get(Object key)
    {
        return(InviteProjectInfo)getObject(key);
    }
    public void set(int index, InviteProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteProjectInfo item)
    {
        return super.indexOf(item);
    }
}