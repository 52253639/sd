package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteTeamCollection extends AbstractObjectCollection 
{
    public InviteTeamCollection()
    {
        super(InviteTeamInfo.class);
    }
    public boolean add(InviteTeamInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteTeamCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteTeamInfo item)
    {
        return removeObject(item);
    }
    public InviteTeamInfo get(int index)
    {
        return(InviteTeamInfo)getObject(index);
    }
    public InviteTeamInfo get(Object key)
    {
        return(InviteTeamInfo)getObject(key);
    }
    public void set(int index, InviteTeamInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteTeamInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteTeamInfo item)
    {
        return super.indexOf(item);
    }
}