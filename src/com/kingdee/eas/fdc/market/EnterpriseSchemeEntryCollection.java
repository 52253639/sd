package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EnterpriseSchemeEntryCollection extends AbstractObjectCollection 
{
    public EnterpriseSchemeEntryCollection()
    {
        super(EnterpriseSchemeEntryInfo.class);
    }
    public boolean add(EnterpriseSchemeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EnterpriseSchemeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EnterpriseSchemeEntryInfo item)
    {
        return removeObject(item);
    }
    public EnterpriseSchemeEntryInfo get(int index)
    {
        return(EnterpriseSchemeEntryInfo)getObject(index);
    }
    public EnterpriseSchemeEntryInfo get(Object key)
    {
        return(EnterpriseSchemeEntryInfo)getObject(key);
    }
    public void set(int index, EnterpriseSchemeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EnterpriseSchemeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EnterpriseSchemeEntryInfo item)
    {
        return super.indexOf(item);
    }
}