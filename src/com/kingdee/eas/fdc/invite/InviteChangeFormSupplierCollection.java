package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteChangeFormSupplierCollection extends AbstractObjectCollection 
{
    public InviteChangeFormSupplierCollection()
    {
        super(InviteChangeFormSupplierInfo.class);
    }
    public boolean add(InviteChangeFormSupplierInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteChangeFormSupplierCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteChangeFormSupplierInfo item)
    {
        return removeObject(item);
    }
    public InviteChangeFormSupplierInfo get(int index)
    {
        return(InviteChangeFormSupplierInfo)getObject(index);
    }
    public InviteChangeFormSupplierInfo get(Object key)
    {
        return(InviteChangeFormSupplierInfo)getObject(key);
    }
    public void set(int index, InviteChangeFormSupplierInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteChangeFormSupplierInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteChangeFormSupplierInfo item)
    {
        return super.indexOf(item);
    }
}