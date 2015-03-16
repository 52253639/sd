package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteAllInformationCollection extends AbstractObjectCollection 
{
    public InviteAllInformationCollection()
    {
        super(InviteAllInformationInfo.class);
    }
    public boolean add(InviteAllInformationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteAllInformationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteAllInformationInfo item)
    {
        return removeObject(item);
    }
    public InviteAllInformationInfo get(int index)
    {
        return(InviteAllInformationInfo)getObject(index);
    }
    public InviteAllInformationInfo get(Object key)
    {
        return(InviteAllInformationInfo)getObject(key);
    }
    public void set(int index, InviteAllInformationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteAllInformationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteAllInformationInfo item)
    {
        return super.indexOf(item);
    }
}