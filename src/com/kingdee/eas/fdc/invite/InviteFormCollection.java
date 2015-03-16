package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteFormCollection extends AbstractObjectCollection 
{
    public InviteFormCollection()
    {
        super(InviteFormInfo.class);
    }
    public boolean add(InviteFormInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteFormCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteFormInfo item)
    {
        return removeObject(item);
    }
    public InviteFormInfo get(int index)
    {
        return(InviteFormInfo)getObject(index);
    }
    public InviteFormInfo get(Object key)
    {
        return(InviteFormInfo)getObject(key);
    }
    public void set(int index, InviteFormInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteFormInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteFormInfo item)
    {
        return super.indexOf(item);
    }
}