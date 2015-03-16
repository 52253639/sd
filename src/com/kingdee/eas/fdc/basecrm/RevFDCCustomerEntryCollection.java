package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RevFDCCustomerEntryCollection extends AbstractObjectCollection 
{
    public RevFDCCustomerEntryCollection()
    {
        super(RevFDCCustomerEntryInfo.class);
    }
    public boolean add(RevFDCCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RevFDCCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RevFDCCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public RevFDCCustomerEntryInfo get(int index)
    {
        return(RevFDCCustomerEntryInfo)getObject(index);
    }
    public RevFDCCustomerEntryInfo get(Object key)
    {
        return(RevFDCCustomerEntryInfo)getObject(key);
    }
    public void set(int index, RevFDCCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RevFDCCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RevFDCCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}