package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteTimeConsultCollection extends AbstractObjectCollection 
{
    public InviteTimeConsultCollection()
    {
        super(InviteTimeConsultInfo.class);
    }
    public boolean add(InviteTimeConsultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteTimeConsultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteTimeConsultInfo item)
    {
        return removeObject(item);
    }
    public InviteTimeConsultInfo get(int index)
    {
        return(InviteTimeConsultInfo)getObject(index);
    }
    public InviteTimeConsultInfo get(Object key)
    {
        return(InviteTimeConsultInfo)getObject(key);
    }
    public void set(int index, InviteTimeConsultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteTimeConsultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteTimeConsultInfo item)
    {
        return super.indexOf(item);
    }
}