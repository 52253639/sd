package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseInviteCollection extends AbstractObjectCollection 
{
    public BaseInviteCollection()
    {
        super(BaseInviteInfo.class);
    }
    public boolean add(BaseInviteInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseInviteCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseInviteInfo item)
    {
        return removeObject(item);
    }
    public BaseInviteInfo get(int index)
    {
        return(BaseInviteInfo)getObject(index);
    }
    public BaseInviteInfo get(Object key)
    {
        return(BaseInviteInfo)getObject(key);
    }
    public void set(int index, BaseInviteInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseInviteInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseInviteInfo item)
    {
        return super.indexOf(item);
    }
}