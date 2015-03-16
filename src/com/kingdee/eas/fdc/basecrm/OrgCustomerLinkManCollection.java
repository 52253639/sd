package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OrgCustomerLinkManCollection extends AbstractObjectCollection 
{
    public OrgCustomerLinkManCollection()
    {
        super(OrgCustomerLinkManInfo.class);
    }
    public boolean add(OrgCustomerLinkManInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OrgCustomerLinkManCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OrgCustomerLinkManInfo item)
    {
        return removeObject(item);
    }
    public OrgCustomerLinkManInfo get(int index)
    {
        return(OrgCustomerLinkManInfo)getObject(index);
    }
    public OrgCustomerLinkManInfo get(Object key)
    {
        return(OrgCustomerLinkManInfo)getObject(key);
    }
    public void set(int index, OrgCustomerLinkManInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OrgCustomerLinkManInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OrgCustomerLinkManInfo item)
    {
        return super.indexOf(item);
    }
}