package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteContentCollection extends AbstractObjectCollection 
{
    public InviteContentCollection()
    {
        super(InviteContentInfo.class);
    }
    public boolean add(InviteContentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteContentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteContentInfo item)
    {
        return removeObject(item);
    }
    public InviteContentInfo get(int index)
    {
        return(InviteContentInfo)getObject(index);
    }
    public InviteContentInfo get(Object key)
    {
        return(InviteContentInfo)getObject(key);
    }
    public void set(int index, InviteContentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteContentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteContentInfo item)
    {
        return super.indexOf(item);
    }
}