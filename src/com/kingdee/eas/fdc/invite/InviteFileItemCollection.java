package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteFileItemCollection extends AbstractObjectCollection 
{
    public InviteFileItemCollection()
    {
        super(InviteFileItemInfo.class);
    }
    public boolean add(InviteFileItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteFileItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteFileItemInfo item)
    {
        return removeObject(item);
    }
    public InviteFileItemInfo get(int index)
    {
        return(InviteFileItemInfo)getObject(index);
    }
    public InviteFileItemInfo get(Object key)
    {
        return(InviteFileItemInfo)getObject(key);
    }
    public void set(int index, InviteFileItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteFileItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteFileItemInfo item)
    {
        return super.indexOf(item);
    }
}