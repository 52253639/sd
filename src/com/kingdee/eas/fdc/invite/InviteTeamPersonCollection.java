package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteTeamPersonCollection extends AbstractObjectCollection 
{
    public InviteTeamPersonCollection()
    {
        super(InviteTeamPersonInfo.class);
    }
    public boolean add(InviteTeamPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteTeamPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteTeamPersonInfo item)
    {
        return removeObject(item);
    }
    public InviteTeamPersonInfo get(int index)
    {
        return(InviteTeamPersonInfo)getObject(index);
    }
    public InviteTeamPersonInfo get(Object key)
    {
        return(InviteTeamPersonInfo)getObject(key);
    }
    public void set(int index, InviteTeamPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteTeamPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteTeamPersonInfo item)
    {
        return super.indexOf(item);
    }
}