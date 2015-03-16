package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvitePurchaseModeCollection extends AbstractObjectCollection 
{
    public InvitePurchaseModeCollection()
    {
        super(InvitePurchaseModeInfo.class);
    }
    public boolean add(InvitePurchaseModeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvitePurchaseModeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvitePurchaseModeInfo item)
    {
        return removeObject(item);
    }
    public InvitePurchaseModeInfo get(int index)
    {
        return(InvitePurchaseModeInfo)getObject(index);
    }
    public InvitePurchaseModeInfo get(Object key)
    {
        return(InvitePurchaseModeInfo)getObject(key);
    }
    public void set(int index, InvitePurchaseModeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvitePurchaseModeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvitePurchaseModeInfo item)
    {
        return super.indexOf(item);
    }
}