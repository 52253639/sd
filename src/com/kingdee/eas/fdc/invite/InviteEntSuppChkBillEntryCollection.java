package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteEntSuppChkBillEntryCollection extends AbstractObjectCollection 
{
    public InviteEntSuppChkBillEntryCollection()
    {
        super(InviteEntSuppChkBillEntryInfo.class);
    }
    public boolean add(InviteEntSuppChkBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteEntSuppChkBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteEntSuppChkBillEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteEntSuppChkBillEntryInfo get(int index)
    {
        return(InviteEntSuppChkBillEntryInfo)getObject(index);
    }
    public InviteEntSuppChkBillEntryInfo get(Object key)
    {
        return(InviteEntSuppChkBillEntryInfo)getObject(key);
    }
    public void set(int index, InviteEntSuppChkBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteEntSuppChkBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteEntSuppChkBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}