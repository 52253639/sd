package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteContractFrameCollection extends AbstractObjectCollection 
{
    public InviteContractFrameCollection()
    {
        super(InviteContractFrameInfo.class);
    }
    public boolean add(InviteContractFrameInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteContractFrameCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteContractFrameInfo item)
    {
        return removeObject(item);
    }
    public InviteContractFrameInfo get(int index)
    {
        return(InviteContractFrameInfo)getObject(index);
    }
    public InviteContractFrameInfo get(Object key)
    {
        return(InviteContractFrameInfo)getObject(key);
    }
    public void set(int index, InviteContractFrameInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteContractFrameInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteContractFrameInfo item)
    {
        return super.indexOf(item);
    }
}