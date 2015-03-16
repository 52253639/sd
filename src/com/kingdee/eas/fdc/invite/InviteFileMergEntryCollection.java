package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteFileMergEntryCollection extends AbstractObjectCollection 
{
    public InviteFileMergEntryCollection()
    {
        super(InviteFileMergEntryInfo.class);
    }
    public boolean add(InviteFileMergEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteFileMergEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteFileMergEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteFileMergEntryInfo get(int index)
    {
        return(InviteFileMergEntryInfo)getObject(index);
    }
    public InviteFileMergEntryInfo get(Object key)
    {
        return(InviteFileMergEntryInfo)getObject(key);
    }
    public void set(int index, InviteFileMergEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteFileMergEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteFileMergEntryInfo item)
    {
        return super.indexOf(item);
    }
}