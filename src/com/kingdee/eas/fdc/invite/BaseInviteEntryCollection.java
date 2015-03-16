package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BaseInviteEntryCollection extends AbstractObjectCollection 
{
    public BaseInviteEntryCollection()
    {
        super(BaseInviteEntryInfo.class);
    }
    public boolean add(BaseInviteEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BaseInviteEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BaseInviteEntryInfo item)
    {
        return removeObject(item);
    }
    public BaseInviteEntryInfo get(int index)
    {
        return(BaseInviteEntryInfo)getObject(index);
    }
    public BaseInviteEntryInfo get(Object key)
    {
        return(BaseInviteEntryInfo)getObject(key);
    }
    public void set(int index, BaseInviteEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BaseInviteEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BaseInviteEntryInfo item)
    {
        return super.indexOf(item);
    }
}