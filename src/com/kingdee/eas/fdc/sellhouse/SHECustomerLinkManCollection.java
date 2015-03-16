package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHECustomerLinkManCollection extends AbstractObjectCollection 
{
    public SHECustomerLinkManCollection()
    {
        super(SHECustomerLinkManInfo.class);
    }
    public boolean add(SHECustomerLinkManInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHECustomerLinkManCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHECustomerLinkManInfo item)
    {
        return removeObject(item);
    }
    public SHECustomerLinkManInfo get(int index)
    {
        return(SHECustomerLinkManInfo)getObject(index);
    }
    public SHECustomerLinkManInfo get(Object key)
    {
        return(SHECustomerLinkManInfo)getObject(key);
    }
    public void set(int index, SHECustomerLinkManInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHECustomerLinkManInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHECustomerLinkManInfo item)
    {
        return super.indexOf(item);
    }
}