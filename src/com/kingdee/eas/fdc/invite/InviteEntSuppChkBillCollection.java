package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteEntSuppChkBillCollection extends AbstractObjectCollection 
{
    public InviteEntSuppChkBillCollection()
    {
        super(InviteEntSuppChkBillInfo.class);
    }
    public boolean add(InviteEntSuppChkBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteEntSuppChkBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteEntSuppChkBillInfo item)
    {
        return removeObject(item);
    }
    public InviteEntSuppChkBillInfo get(int index)
    {
        return(InviteEntSuppChkBillInfo)getObject(index);
    }
    public InviteEntSuppChkBillInfo get(Object key)
    {
        return(InviteEntSuppChkBillInfo)getObject(key);
    }
    public void set(int index, InviteEntSuppChkBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteEntSuppChkBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteEntSuppChkBillInfo item)
    {
        return super.indexOf(item);
    }
}