package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteFileMergeCollection extends AbstractObjectCollection 
{
    public InviteFileMergeCollection()
    {
        super(InviteFileMergeInfo.class);
    }
    public boolean add(InviteFileMergeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteFileMergeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteFileMergeInfo item)
    {
        return removeObject(item);
    }
    public InviteFileMergeInfo get(int index)
    {
        return(InviteFileMergeInfo)getObject(index);
    }
    public InviteFileMergeInfo get(Object key)
    {
        return(InviteFileMergeInfo)getObject(key);
    }
    public void set(int index, InviteFileMergeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteFileMergeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteFileMergeInfo item)
    {
        return super.indexOf(item);
    }
}