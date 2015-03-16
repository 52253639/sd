package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteChangeFormCollection extends AbstractObjectCollection 
{
    public InviteChangeFormCollection()
    {
        super(InviteChangeFormInfo.class);
    }
    public boolean add(InviteChangeFormInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteChangeFormCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteChangeFormInfo item)
    {
        return removeObject(item);
    }
    public InviteChangeFormInfo get(int index)
    {
        return(InviteChangeFormInfo)getObject(index);
    }
    public InviteChangeFormInfo get(Object key)
    {
        return(InviteChangeFormInfo)getObject(key);
    }
    public void set(int index, InviteChangeFormInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteChangeFormInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteChangeFormInfo item)
    {
        return super.indexOf(item);
    }
}